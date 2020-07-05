#! /bin/bash

#curl -X POST https://api.telegram.org/bot576734993:AAFhJQALvnwzXR8ZWlLGkRaZePlUcGGwMmA/sendMessage -d chat_id=-1001495505532 \
#-d "text=${GITHUB_ACTOR} successfully pushed branch ${GITHUB_REF##*/}"
curl -X POST https://api.telegram.org/bot${BOT_ID}/sendMessage -d chat_id=${CHAT_ID} \
-d "text=${GITHUB_ACTOR} successfully pushed branch ${GITHUB_REF##*/}"

echo trying to output env vars:
echo actor is ${GITHUB_ACTOR}
echo branch is ${GITHUB_REF##*/}