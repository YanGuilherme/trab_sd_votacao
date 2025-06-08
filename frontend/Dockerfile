# Etapa 1: Build da aplicação Angular
FROM node:20-slim AS builder

WORKDIR /app
COPY package*.json ./
RUN npm install -g @angular/cli@19.2.0
RUN npm install
COPY . .
RUN ng build --configuration production

# Etapa 2: Servir a aplicação com NGINX
FROM nginx:1.25-alpine

RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /app/dist/sistema-votacao /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
RUN chmod -R 755 /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
