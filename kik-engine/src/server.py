import os
import time

from dotenv import load_dotenv
from flask import Flask, request
from kik_unofficial.datatypes.exceptions import KikApiException

from bot import EchoBot

app = Flask(__name__)
global bot

load_dotenv()

userList = {}


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
    user_message = data["user_message"]
    print(data)
    user_checked = {}

    for key, value in user_message.items():
        key = key.lower()
        try:
            if bot.client.get_jid(key) is None:
                return {"status": "error", "message": f"User {key} not found"}, 400
            else:
                user_checked[key] = value
        except KikApiException:
            return {"status": "error",
                    "message": f"An error occurred while checking user {key}, username not found"}, 500
        except TimeoutError:
            return {"status": "error",
                    "message": f"Timeout occurred while checking user {key}, username not found"}, 500
    for key, value in user_checked.items():
        print(key, value)
        bot.send_template_messages(key, value)
    return {"status": "success"}, 200


@app.route('/get_chat_status', methods=['GET'])
def get_chat_status():
    return bot.user_message_status, 200


if __name__ == '__main__':
    main()
