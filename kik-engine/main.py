#!/usr/bin/env python3
import os
import time

from dotenv import load_dotenv

from kik_unofficial.client import KikClient
from kik_unofficial.callbacks import KikClientCallback
from kik_unofficial.datatypes.xmpp.errors import SignUpError, LoginError

from kik_unofficial.datatypes.xmpp.login import ConnectionFailedResponse
from flask import Flask, request

load_dotenv()


userList = {}


def main():
    start_bot()
    app.run(use_reloader=False)
    while True:
        time.sleep(5)


app = Flask(__name__)
global bot


def start_bot():
    global bot

    creds = {
        "username": os.getenv("KIK_USERNAME"),
        "password": os.getenv("KIK_PASSWORD"),
        "device_id": os.getenv("KIK_DEVICE_ID"),
    }

    bot = EchoBot(creds)


@app.route('/send_message', methods=['POST'])
def send_message_to_users():
    data = request.get_json()

    bot.send_template_messages()


class EchoBot(KikClientCallback):
    def __init__(self, creds: dict):
        username = creds["username"]
        password = creds.get("password") or input("Enter your password: ")

        device_id = creds["device_id"]

        self.client = KikClient(self, username, str(password), device_id, enable_console_logging=True)

    # Error Handling
    def on_connection_failed(self, response: ConnectionFailedResponse):
        self.client.log.error(f"Connection failed: {response.message}")

    def on_login_error(self, login_error: LoginError):
        if login_error.is_captcha():
            login_error.solve_captcha_wizard(self.client)

    def on_register_error(self, response: SignUpError):
        self.client.log.error(f"Register error: {response.message}")

    def send_template_messages(self, max_retries: int = 5):
        for user in userList:
            self.client.send_chat_message(user, "I love you")


if __name__ == "__main__":
    main()
