#!/usr/bin/env bash
set -euo pipefail
source ./bash_variables.sh

send_to_all() {
  local key="$1"

  for ID in $(seq 1 "${NUM_NODES}") ; do
    NODE="${NODE_IP[$ID]}"
    SESSION="${SESSION_PREFIX}_${ID}"

    echo "[NODE_${ID} @ ${NODE}] sending '${key}' to tmux session '${SESSION}'"

    # Only send if the session exists
    sshpass -p "${DSV_PASS}" ssh -o StrictHostKeyChecking=no "${REMOTE_USER}@${NODE}" -- \
      "tmux has-session -t '${SESSION}' 2>/dev/null && tmux send-keys -t '${SESSION}' '${key}' Enter || true"
  done
}

# 1) send e everywhere
send_to_all "e"

# optional small pause so your app can react before sending the next command
sleep 1

# 2) then send l everywhere
send_to_all "l"
