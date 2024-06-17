# CodeFusion Spine COMSAT

A fully fletched outreach platform built for [spine.ngo](https://spine.ngo).

Built with Angular@18 and SpringBoot@3.2.5

## Production

This will build a docker image for comsat_kik_engine (kik-engine), comsat_frontend (web) and comsat_backend (comsat).
To build the production version of the application, run the following command:

```bash
docker compose build
```

To then run those containers including a database, run the following command:

```bash
docker compose --env-file .env up
```

The .env file can be copied from the .env.example file.

A reverse proxy is included in the form of a Caddy container which acts as a reverse proxy for the other containers. The only configuration required is to set the `CORS_ALLOWED_ORIGINS` environment variable to the domain of the frontend application.

## Development

To run the application in development mode, make sure of the following:

### Backend

    - You have a postgres database running locally
    - For the backend make sure that a valid application.properties file is present in the resources directory. this can be copied from the application.properties.example file.

### Kik-engine

    - The kik-bot-api-unofficial library is installed as a git submodule with `git clone -b new https://github.com/tomer8007/kik-bot-api-unofficial`
    - The requirements are installed with `pip install -r requirements.txt`
    - You have a kik account
    - For the kik-engine to make sure that a .env is filled in properly with valid kik credentials, this can be copied from the .env.example file.

### Frontend

    - All the dependencies are installed with npm install
    - Run with npm run start

The frontend will in production look for the backend at `{origin}/api/v1`
