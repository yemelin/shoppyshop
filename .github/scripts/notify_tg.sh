#! /bin/bash

# add to secrets and remove
BOT_ID=576734993:AAFhJQALvnwzXR8ZWlLGkRaZePlUcGGwMmA
CHAT_ID=-1001174776301

BRANCH_NAME=${GITHUB_REF##*/}

# this script is currently used for PR notifications only, so we keep it simple while we can
# once push notification is needed, branch extraction below will come in handy
# "branch: [${BRANCH_NAME}](https://github.com/${GITHUB_REPOSITORY}/tree/${BRANCH_NAME})"

read -r -d '' MSG <<- EOM
*event*: ${GITHUB_EVENT_NAME}
*actor*: ${GITHUB_ACTOR}
*action*: ${ACTION}
"*PR*: [${PR_TITLE}](${PR_LINK})"
EOM

# escaping _ and - for Telegram to parse Markdown correctly
MSG=${MSG//-/\\-}
MSG=${MSG//_/\\_}

echo "sending ${MSG}"
curl -X POST https://api.telegram.org/bot${BOT_ID}/sendMessage -d chat_id="${CHAT_ID}" \
-d "text=${MSG}" -d parse_mode=MarkdownV2 -d disable_web_page_preview=true


