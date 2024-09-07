# front

# Шаг 1. Скачиваем и устанавливаем NodeJS.
FROM node:alpine AS builder

user root

# Шаг 3. Задаём директорию, в которой будет лежать наше приложение в виртуальном Линуксе.
WORKDIR /app

RUN chmod -R 777 /app

# Шаг 5. Копируем данные из образа в рабочую папку.
COPY . .

# Щаг 6. Запускаем менеджер пакетов NodeJS.
RUN npm install

RUN npm run build

FROM nginx:stable-alpine

COPY --from=builder /app/nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=builder /app/dist /usr/share/nginx/html

# Expose port 80 for Nginx
EXPOSE 80

# Запуск nginx
CMD ["nginx", "-g", "daemon off;"]

