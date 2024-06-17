import os
import threading

from dotenv import load_dotenv
from flask import Flask, request
from kik_unofficial.datatypes.exceptions import KikApiException

from bot import EchoBot
from contact import Contact
from message_queue import Queue

app = Flask(__name__)
load_dotenv()

queue = Queue()


def create_queue():
    while True:
        if queue.size() > 0:
            contact = queue.dequeue()
            print(contact)
            send_message_to_contact(contact)


def send_message_to_contact(contact: Contact):
    bot.add_contact(contact)

    try:
        if bot.client.get_jid(contact.username) is None:
            bot.set_contact_error_status(contact, 'username not found')
        else:
            bot.send_template_messages(
                contact.username, contact.batch_id, contact.message)
            print(contact)

    except KikApiException:
        bot.set_contact_error_status(contact, "Username not found")
    except TimeoutError:
        bot.set_contact_error_status(contact, "Username not found")


def start_bot():
    global bot

    creds = {
        "username": os.getenv("KIK_USERNAME"),
        "password": os.getenv("KIK_PASSWORD"),
        "device_id": os.getenv("KIK_DEVICE_ID"),
    }

    bot = EchoBot(creds)


#   POST /send_message
#   Content-Type: application/json
#
#   { username: string, message: string, batchId: string }[]
#
#   -> 200 OK { status: string }

@app.route('/send_message', methods=['POST'])
def send_message_to_users():
    data = request.get_json()

    for contactData in data:
        contact = Contact(
            contactData["username"], contactData["message"], contactData["batchId"])
        queue.enqueue(contact)

    return {"status": "success"}, 200


#   GET /get_chat_status
#
#   -> 200 OK { username: string, status: string, message: string, messageId: string, batchId: string }[]

@app.route('/get_chat_status', methods=['GET'])
def get_chat_status():
    return bot.user_message_status, 200


#   GET /status
#
#   -> 200 OK { connected: bool, authenticated: bool, captcha_url: string }

@app.route('/status', methods=['GET'])
def get_context():
    return bot.get_context(), 200


#   POST /captcha
#   Content-Type: application/json
#
#   { token: string } everything after response= from the return url
#
#   -> 200 OK { status: string }

@app.route('/captcha', methods=['POST'])
def send_captcha():
    data = request.get_json()
    token = data["token"]
    bot.solve_captcha(token)

    return {"status": "success"}, 200


def main():
    start_bot()
    threading.Thread(target=create_queue).start()
    app.run(
        use_reloader=False,
        debug=os.getenv("DEBUG", True),
        port=os.getenv("PORT"),
        host=os.getenv("HOST", "0.0.0.0")
    )


if __name__ == '__main__':
    main()
