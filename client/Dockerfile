FROM nginx

WORKDIR /usr/share/nginx/html
USER root

COPY ./user-management-app/dist /usr/share/nginx/html

COPY ./nginx/nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]