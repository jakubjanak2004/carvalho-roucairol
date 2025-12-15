#!/usr/bin/env bash
set -euo pipefail

read -r -p "IP address: " IP
read -r -p "Node number (e.g. 1 -> NODE_1): " N

SESSION="NODE_${N}"

# -t forces a pseudo-terminal so tmux can attach
ssh -t "dsva@${IP}" "tmux attach -t '${SESSION}'"
