from dataclasses import dataclass


@dataclass
class Contact:
    username: str
    message: str
    batch_id: str
    message_id: str = None
    status: str = 'queued'
