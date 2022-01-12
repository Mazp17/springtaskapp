FROM node:17-alpine as build-step

RUN mkdir -p /app

WORKDIR /app

COPY package.json /app

RUN npm install 

COPY . /app

RUN npm run build --prod

#Second Step

FROM nginx:1.17.1-alpine 

COPY --from=build-step /app/dist/taskapp /usr/share/nginx/html