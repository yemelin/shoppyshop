#! /bin/bash

curl -X POST https://api.telegram.org/bot576734993:AAFhJQALvnwzXR8ZWlLGkRaZePlUcGGwMmA/sendMessage -d chat_id=-1001495505532 \
-d "text=dummy experimental bot message"

echo trying to output env vars:
echo repo is ${github.repository}
echo actor is ${ACTOR}