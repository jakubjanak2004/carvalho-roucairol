#!/usr/bin/env bash
set -euo pipefail

read -r -p "IP address: " IP
read -r -p "Node number (e.g. 1 -> NODE_1): " N

SESSION="NODE_${N}"

# Connect and attach. (If the session doesn't exist, tmux will print an error.)
ssh "dsva@${IP}" "tmux attach -t '${SESSION}'"
