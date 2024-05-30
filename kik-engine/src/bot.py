from kik_unofficial.callbacks import KikClientCallback
from kik_unofficial.client import KikClient
from kik_unofficial.datatypes.xmpp import chatting
from kik_unofficial.datatypes.xmpp.errors import LoginError, SignUpError
from kik_unofficial.datatypes.xmpp.login import ConnectionFailedResponse

from contact import Contact


def jid_to_username(jid):
    return jid.split("@")[0][:-4]


class EchoBot(KikClientCallback):
    user_message_status = {}

    def __init__(self, creds: dict):
        username = creds["username"]
        password = creds.get("password") or input("Enter your password: ")
        device_id = creds["device_id"]

        self.client = KikClient(self, username, str(password), device_id, enable_console_logging=True)

    def set_contact_error_status(self, contact: Contact, error: str):
        self.user_message_status[contact.username] = error

    def on_connection_failed(self, response: ConnectionFailedResponse):
        self.client.log.error(f"Connection failed: {response.message}")

    def on_login_error(self, login_error: LoginError):
        if login_error.is_captcha():
            login_error.solve_captcha_wizard(self.client)

    def on_register_error(self, response: SignUpError):
        self.client.log.error(f"Register error: {response.message}")

    def send_template_messages(self, username, message):
        self.client.send_chat_message(username, message)
        self.user_message_status[username] = 'sent'
        print(self.user_message_status)

    def on_message_read(self, read: chatting.IncomingMessageReadEvent):
        self.user_message_status[jid_to_username(read.from_jid)] = 'read'
        print(self.user_message_status)

    def on_message_delivered(self, delivered: chatting.IncomingMessageDeliveredEvent):
        self.user_message_status[jid_to_username(delivered.from_jid)] = 'delivered'
        print(self.user_message_status)

    def on_chat_message_received(self, message: chatting.IncomingChatMessage):
        self.user_message_status[jid_to_username(message.from_jid)] = 'answered'
        print(self.user_message_status)
