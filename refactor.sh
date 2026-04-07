#!/usr/bin/env bash

echo "Editing $1"
cat<<TXT | nvim -es "$1"
%s@fr\(.\)progilone\(.\)pgcn@org\1numahop\2numahop@g
x
TXT
