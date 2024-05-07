FROM node:20-alpine as angular
WORKDIR /ng-app
COPY package*.json .
RUN npm ci --force
COPY . .
RUN npm run build

FROM nginx:alpine
ARG name
COPY --from=angular /ng-app/dist/comsat/browser /usr/share/nginx/html
COPY --from=angular /ng-app/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
