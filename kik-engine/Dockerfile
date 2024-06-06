FROM python:3-slim

WORKDIR /etc/kik-engine

RUN apt-get update && apt-get install -y git

RUN git clone -b new https://github.com/tomer8007/kik-bot-api-unofficial.git

COPY requirements.txt .

RUN pip install --no-cache-dir -r requirements.txt

COPY . .

WORKDIR /etc/kik-engine/src

RUN ls -la

ENTRYPOINT ["python", "./server.py"]