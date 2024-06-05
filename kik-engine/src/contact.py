from dataclasses import dataclass


@dataclass
class Contact:
    username: str
    message: str
    batch_id: str
    status: str = 'queued'
