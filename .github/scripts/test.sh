#! /bin/bash

GITHUB_ACTOR=yemelin
GITHUB_REF=some_branch
echo ${ACTOR}
echo \`${ACTOR}\`

echo "text=\\\`${GITHUB_ACTOR}\` successfully pushed branch \`${GITHUB_REF##*/}\`"

read -r -d '' MSG <<- EOM
WTF
WTF
${GITHUB_ACTOR}
${GITHUB_REF}
EOM

echo "${MSG}"