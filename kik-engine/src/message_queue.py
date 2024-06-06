class Queue:
    def __init__(self):
        self._queue = []

    def enqueue(self, item):
        self._queue.append(item)

    def dequeue(self):
        return self._queue.pop(0)

    def is_empty(self):
        return len(self._queue) == 0

    def size(self):
        return len(self._queue)

