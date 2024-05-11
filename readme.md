# CodeFusion Spine COMSAT

A fully fletched outreach platform built for [spine.ngo](https://spine.ngo).

Built with Angular@17 and SpringBoot@3.2.5

## Production

This will build a docker image for comsat_frontend (web) and comsat_backend (comsat).
To build the production version of the application, run the following command:

```bash
docker compose build
```

To then run those containers including a database, run the following command:

```bash
docker compose --env-file .env up
```

There should be a reverse proxy setup so the frontend and backend can communicate over the same TLD.
