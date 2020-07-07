#! /bin/bash

# add to secrets and remove
BOT_ID=576734993:AAFhJQALvnwzXR8ZWlLGkRaZePlUcGGwMmA
CHAT_ID=-1001174776301

BRANCH_NAME=${GITHUB_REF##*/}

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
