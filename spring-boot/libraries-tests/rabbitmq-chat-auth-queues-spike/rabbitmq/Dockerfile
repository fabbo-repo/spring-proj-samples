FROM rabbitmq:3.12

RUN /bin/sh -c "rabbitmq-plugins enable rabbitmq_management"
RUN /bin/sh -c "rabbitmq-plugins enable rabbitmq_web_stomp"
RUN /bin/sh -c "rabbitmq-plugins enable rabbitmq_auth_backend_http"

COPY config/auth-backend.conf /etc/rabbitmq/conf.d/auth-backend.conf
