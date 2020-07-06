#! /bin/bash

#curl -X POST https://api.telegram.org/bot576734993:AAFhJQALvnwzXR8ZWlLGkRaZePlUcGGwMmA/sendMessage -d chat_id=-1001495505532 \
#-d "text=${GITHUB_ACTOR} successfully pushed branch ${GITHUB_REF##*/}"
BRANCH_NAME=${GITHUB_REF##*/}
echo "${GITHUB_EVENT_NAME}"

if [ "${GITHUB_EVENT_NAME}" != "push" ]; then
  curl -X POST https://api.telegram.org/bot${BOT_ID}/sendMessage -d chat_id=${CHAT_ID} \
  -d "text=something PR-related happened, probably ${ACTION}"
  exit 0
fi

echo "text=\`${GITHUB_ACTOR}\` *successfully* __pushed__ branch [${BRANCH_NAME//-/\\-}](${GITHUB_REPOSITORY}/tree/${BRANCH_NAME//-/\\-})"
curl -X POST https://api.telegram.org/bot${BOT_ID}/sendMessage -d chat_id=${CHAT_ID} \
-d "text=\`${GITHUB_ACTOR}\` *successfully* __pushed__ branch [${BRANCH_NAME//-/\\-}](https://github.com/${GITHUB_REPOSITORY}/tree/${BRANCH_NAME//-/\\-})" -d parse_mode=MarkdownV2

#echo trying to output env vars:
#echo actor is ${GITHUB_ACTOR}
#echo branch is ${GITHUB_REF##*/}