#! /bin/bash

GITHUB_ACTOR=yemelin
GITHUB_REF=some_branch
echo ${ACTOR}
echo \`${ACTOR}\`

echo "text=\\\`${GITHUB_ACTOR}\` successfully pushed branch \`${GITHUB_REF##*/}\`"