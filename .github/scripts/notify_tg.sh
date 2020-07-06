#! /bin/bash

#curl -X POST https://api.telegram.org/bot576734993:AAFhJQALvnwzXR8ZWlLGkRaZePlUcGGwMmA/sendMessage -d chat_id=-1001495505532 \
#-d "text=${GITHUB_ACTOR} successfully pushed branch ${GITHUB_REF##*/}"
BRANCH_NAME=${GITHUB_REF##*/}
echo "${GITHUB_EVENT_NAME}"

read -r -d '' HEADER <<- EOM
*event*: ${GITHUB_EVENT_NAME}
*actor*: ${GITHUB_ACTOR}
EOM

if [ -n "${ACTION}" ]; then
  read -r -d '' HEADER <<- EOM
  ${HEADER}
  *action*: ${ACTION}
EOM
fi

case "${GITHUB_EVENT_NAME}" in
  "push")
  SUMMARY="branch: [${BRANCH_NAME}](https://github.com/${GITHUB_REPOSITORY}/tree/${BRANCH_NAME})"
    ;;
  "pull_request")
  SUMMARY="*PR*: [${PR_TITLE}](${PR_LINK})"
    ;;
  "pull_request_review")
  SUMMARY="some day here will be PR link"
    ;;
  *)
    echo "no notification message for event: ${GITHUB_EVENT_NAME}"
    exit 1
    ;;
esac

read -r -d '' MSG <<- EOM
${HEADER}
${SUMMARY}
EOM

MSG=${MSG//-/\\-}
MSG=${MSG//_/\\_}
echo "sending ${MSG}"
curl -X POST https://api.telegram.org/bot${BOT_ID}/sendMessage -d chat_id="${CHAT_ID}" \
-d "text=${MSG}" -d parse_mode=MarkdownV2 -d disable_web_page_preview=true


#if [ "${GITHUB_EVENT_NAME}" != "push" ]; then
#  curl -X POST https://api.telegram.org/bot${BOT_ID}/sendMessage -d chat_id=${CHAT_ID} \
#  -d "text=something PR-related happened, probably ${ACTION}"
#  exit 0
#fi
#
#echo "text=\`${GITHUB_ACTOR}\` *successfully* __pushed__ branch [${BRANCH_NAME//-/\\-}](${GITHUB_REPOSITORY}/tree/${BRANCH_NAME//-/\\-})"
#curl -X POST https://api.telegram.org/bot${BOT_ID}/sendMessage -d chat_id=${CHAT_ID} \
#-d "text=\`${GITHUB_ACTOR}\` *successfully* __pushed__ branch [${BRANCH_NAME//-/\\-}](https://github.com/${GITHUB_REPOSITORY}/tree/${BRANCH_NAME//-/\\-})" -d parse_mode=MarkdownV2 -d disable_web_page_preview=true

#echo trying to output env vars:
#echo actor is ${GITHUB_ACTOR}
#echo branch is ${GITHUB_REF##*/}