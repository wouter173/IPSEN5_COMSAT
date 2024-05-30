import os
import threading
import time
from dataclasses import dataclass

from dotenv import load_dotenv
from flask import Flask, request
from kik_unofficial.datatypes.exceptions import KikApiException

from bot import EchoBot
from src.contact import Contact
from src.message_queue import Queue

app = Flask(__name__)
load_dotenv()

queue = Queue()
userList = {}


def create_queue():
    while True:
        if queue.size() > 0:
            contact = queue.dequeue()
            print(contact)
            send_message_to_contact(contact)


def send_message_to_contact(contact: Contact):
    try:
        if bot.client.get_jid(contact.username) is None:
            bot.set_contact_error_status(contact, 'username not found')
        else:
            bot.send_template_messages(contact.username, contact.message)

    except KikApiException:
        bot.set_contact_error_status(contact,
                                     f"Username not found")
    except TimeoutError:
        bot.set_contact_error_status(contact,
                                     f"Username not found")


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

    for key, value in user_message.items():
        key = key.lower()
        queue.enqueue(Contact(key, value))

    return {"status": "success"}, 200


@app.route('/get_chat_status', methods=['GET'])
def get_chat_status():
    return bot.user_message_status, 200


def main():
    start_bot()
    threading.Thread(target=create_queue).start()
    app.run(use_reloader=False, debug=True)


if __name__ == '__main__':
    main()
