from kik_unofficial.callbacks import KikClientCallback
from kik_unofficial.client import KikClient
from kik_unofficial.datatypes.xmpp import chatting
from kik_unofficial.datatypes.xmpp.errors import LoginError, SignUpError
from kik_unofficial.datatypes.xmpp.login import ConnectionFailedResponse

from contact import Contact


def jid_to_username(jid):
    return jid.split("@")[0][:-4]


class EchoBot(KikClientCallback):
    user_message_status = []
    captcha_url: str | None = None

    def __init__(self, creds: dict):
        username = creds["username"]
        password = creds["password"]
        device_id = creds["device_id"]

        self.client = KikClient(self,
                                username,
                                password,
                                device_id,
                                enable_console_logging=True)

    def get_context(self):
        return {
            "authenticated": self.client.authenticated,
            "connected": self.client.connected,
            "captcha_url": self.captcha_url
        }

    #
    # Connection callbacks

    def solve_captcha(self, token):
        self.client.login(self.client.username, self.client.password, token)
        self.captcha_url = None

    def on_login_error(self, login_error: LoginError):
        if login_error.is_captcha():
            self.captcha_url = login_error.captcha_url

    def on_connection_failed(self, response: ConnectionFailedResponse):
        self.client.log.error(f"Connection failed: {response.message}")

    def on_register_error(self, response: SignUpError):
        self.client.log.error(f"Register error: {response.message}")

    #
    # Contact logic

    def add_contact(self, contact: Contact):
        self.user_message_status.append(contact)

    def set_contact_message_id(self, username, batch_id, message_id):
        for entry in self.user_message_status:
            if entry.username == username and entry.batch_id == batch_id:
                entry.message_id = message_id

    def set_contact_status(self, username, message_id, status):
        for entry in self.user_message_status:
            if entry.username == username:
                print(entry.message_id, message_id,
                      entry.message_id == message_id)
                if entry.message_id == message_id:
                    print("Updating status", entry.status, status)
                    entry.status = status
                    print(entry)
                    break

    def set_contact_status_to_replied(self, username):
        for entry in self.user_message_status:
            if entry.username == username and entry.status == 'read':
                entry.status = 'replied'
                break

    def set_contact_error_status(self, contact: Contact, error: str):
        self.set_contact_status(contact.username, error)

    #
    # Message callbacks

    def send_template_messages(self, username, batch_id, message):
        message_id = self.client.send_chat_message(username, message)
        self.set_contact_message_id(username, batch_id, message_id)
        self.set_contact_status(username, message_id, 'sent')

    def on_message_read(self, read_message: chatting.IncomingMessageReadEvent):
        username = jid_to_username(read_message.from_jid)
        self.set_contact_status(
            username, read_message.receipt_message_id, 'read')

    def on_message_delivered(self, delivered_message: chatting.IncomingMessageDeliveredEvent):
        username = jid_to_username(delivered_message.from_jid)
        self.set_contact_status(
            username, delivered_message.receipt_message_id, 'delivered')

    def on_chat_message_received(self, message: chatting.IncomingChatMessage):
        username = jid_to_username(message.from_jid)
        self.set_contact_status_to_replied(username)
