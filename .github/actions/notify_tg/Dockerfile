FROM alpine

RUN apk --no-cache add curl

ADD notify_tg.sh /notify_tg.sh
RUN chmod +x notify_tg.sh

ENTRYPOINT ["/notify_tg.sh"]