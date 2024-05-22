#!/usr/bin/env python3
import os
import time

from flask import Flask, request
from dotenv import load_dotenv

from kik_unofficial.client import KikClient
from kik_unofficial.callbacks import KikClientCallback
from kik_unofficial.datatypes.exceptions import KikApiException
from kik_unofficial.datatypes.xmpp.errors import SignUpError, LoginError
from kik_unofficial.datatypes.xmpp.login import ConnectionFailedResponse

global bot

load_dotenv()

userList = {}

app = Flask(__name__)


def main():
    start_bot()
    app.run(use_reloader=False, debug=True)
    while True:
        time.sleep(5)


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
    users = data.get("users")
    message = data.get("message")

    user_checked = []

    for user in users:
        try:
            if bot.client.get_jid(user) is None:
                return {"status": "error", "message": f"User {user} not found"}, 400
            else:
                user_checked.append(user)
        except KikApiException:
            return {"status": "error",
                    "message": f"An error occurred while checking user {user}, username not found"}, 500
        except TimeoutError:
            return {"status": "error",
                    "message": f"Timeout occurred while checking user {user}, username not found"}, 500

    bot.send_template_messages(user_checked, message)
    return {"status": "success"}, 200


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

    def send_template_messages(self, usernames, message):
        for user in usernames:
            self.client.send_chat_message(user, message)


if __name__ == "__main__":
    main()
