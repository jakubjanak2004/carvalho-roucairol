#!/usr/bin/env bash
set -euo pipefail
source ./bash_variables.sh

for ID in $(seq 1 "${NUM_NODES}") ; do
  NODE="${NODE_IP[$ID]}"
  SESSION="${SESSION_PREFIX}_${ID}"

  sshpass -p "${DSV_PASS}" ssh -o StrictHostKeyChecking=no "${REMOTE_USER}@${NODE}" -- \
    "tmux kill-session -t '${SESSION}' 2>/dev/null || true"
done
