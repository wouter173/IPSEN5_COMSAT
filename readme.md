# CodeFusion Spine COMSAT

A outreach platform built for [spine.ngo](https://spine.ngo).

Built with Angular@18 and SpringBoot@3.2.5

## 1. Run in Development

To run the application in development mode, make sure of the following:

### 1.1 Backend

    - You have a postgres database running locally
    - For the backend make sure that a valid application.properties file is present in the resources directory. this can be copied from the application.properties.example file.

### 1.2 Kik-engine

    - The kik-bot-api-unofficial library is installed as a git submodule with `git clone -b new https://github.com/tomer8007/kik-bot-api-unofficial`
    - The requirements are installed with `pip install -r requirements.txt`
    - You have a kik account
    - For the kik-engine to make sure that a .env is filled in properly with valid kik credentials, this can be copied from the .env.example file.

### 1.3 Frontend

    - All the dependencies are installed with npm install
    - Run with npm run start

The frontend will in production look for the backend at `{origin}/api/v1`

## 2. Deploy to Production

This will build a docker image for comsat_kik_engine (kik-engine), comsat_frontend (web) and comsat_backend (comsat).
To build the production version of the application, run the following command:

A reverse proxy is included in the form of a Caddy container which acts as a reverse proxy for the other containers. The only configuration required is to set the `CORS_ALLOWED_ORIGINS` environment variable to the domain of the frontend application.

### 2.1 Build

```bash
docker compose build
```

### 2.2 Run

To then run those containers including a database, run the following command:

```bash
docker compose --env-file .env up
```

## 3. Environment Variables

The .env file can be copied from the .env.example file.
A thorough explanation of the environment variables is stated below:

```env
# Database configuration
DATABASE_NAME=postgres # this is the name of your database, if you plan to run multiple applications from te same database, make sure to change this to somethine unique
DATABASE_USERNAME=postgres # this is the username of the postgres use that will be used to connect to the database, when using a pre existing database, make sure to change this to the username of the database. However when using the provided docker-compose file, a new database will be created and the username and password will be set to postgres
DATABASE_PASSWORD=PASSWORD # same rules apply to this as the username, when using a pre existing database, make sure to change this to the password of the database. If you ever plan to put the database in production, make sure to change this to a strong password as when a db gets exposed to the internet it should be protected with a strong password

# Backend configuration
JWT_SECRET=LONG_RANDOM_JWT_SECRET # this is the secret used to sign the JWTs, it should generated with an (at least) 32 character long random string generator like: `openssl rand -base64 32`
PORT=8080 # this is the port that the backend will be listening on, only necessary when running the backend in an environment where port 8080 is unavailable (not applicable to the provided docker-compose)
CORS_ALLOWED_ORIGINS= # this is the list of origins that are allowed to make requests to the backend, this should be set to the domain of the frontend application

# Kik Engine configuration
KIK_ENGINE_USERNAME= # this is the username of the kik account that will be used to send messages
KIK_ENGINE_PASSWORD= # this is the password of the kik account that will be used to send messages
KIK_ENGINE_DEVICE_ID= # this is randomly assigned if not provided, however it is advisable to provide it since it helps with circumventing the captchas sometimes. It does also help with keeping the captchas to their minimum 2. It should be an android device id, for example: "0657a828a83f1e67c88efa55cf700789". These can be found in the settings of any android device but, a random provided one is also fine as long as it is unique to kik and formatted correctly.
KIK_ENGINE_PORT=5001 # this is the port that the kik engine will be listening on, only necessary when running the kik engine in an environment where port 5001 is unavailable (not applicable to the provided docker-compose)
```

## 4. Using the Application

The application can be accessed at the url assigned to your reverse proxy (caddy in the case of the provided docker-compose). The application will be available at `{origin}/api/v1` where `{origin}` is the domain of the frontend application. The origin should also be added to the CORS_ALLOWED_ORIGINS environment variable in the .env file.

### 4.1 Authentication

To authenticate as a user, the user must first be created. The first user should be seeded into the database which can be done manually by accessing the database via a tool like pgadmin. Keep in mind that the user should have the admin role with all the permissions currently available (4194303), and that the password should be a bcrypt hash of the password provided.

Once the user is created, the password can be changed by accessing the user's page and changing the password. The admin user can also be used to create new users.

### 4.2 Starting Kik Engine

On the engines page, the kik engine should be accesible as a service.

1. The kik engine should be started by running the docker container.
2. Once the kik engine becomes available in the dashboard, the captcha should be solved by pressing the button on the dashboard.
3. When the captcha is solved, the kik engine should be running and the dashboard should be available.
4. If the kik engine does not show that it is fully authorized, please refresh the engine by clicking the refresh button on the dashboard.
5. If this does not resolve the issue, please try solving the captcha again.

### 4.3 Importing Batches

Batches are imported from a json file with the following format:

```json
[
  {
    "id": "1", // this is the id of the user that will be created
    "firstName": "John Doe", // this is the first name of the user
    "nickname": "hsltest", // this is the nickname of the user on the provided platform
    "platform": "kik", // this is the platform that the user will be created on (kik, whatsapp, telegram, messenger)
    "audience": "working-class", // this is optional and can be used to filter and analyse the sent messages and contacts
    "sex": "Male", // this is optional and can be used to filter and analyse the sent messages and contacts
    "language": "German", // this should be the language of the user, it is used to send correct language specific messages to the user
    "region": "Noordrijn-Westfalen" // this is optional and can be used to filter and analyse the sent messages and contacts
  }
]
```

### 4.4 Sending Batches

1. Once a batch is imported successfully, the batch will go into a review state where the contacts can be edited still.
2. A batch needs certain configurations for which template should be send to each platform.
3. The batch can be sent by clicking the send button on the batch page.
4. Once the batch is sent, the batch will not be editable anymore and the template messages will be sent to contacts.

### 4.5 Templates

1. Templates are used to template the messages that will be sent to the contacts.
2. These can be created and edited by whomever has the permission to do so.
3. Templates can be created, edited and deleted on the template page.
