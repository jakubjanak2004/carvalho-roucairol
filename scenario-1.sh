#!/usr/bin/env bash
set -euo pipefail
source ./bash_variables.sh

send_to_one() {
  local ID="$1"
  local key="$2"

  local NODE="${NODE_IP[$ID]}"
  local SESSION="${SESSION_PREFIX}_${ID}"

  echo "[NODE_${ID} @ ${NODE}] sending '${key}' to tmux session '${SESSION}'"

  # Only send if the session exists
  sshpass -p "${DSV_PASS}" ssh -o StrictHostKeyChecking=no "${REMOTE_USER}@${NODE}" -- \
    "tmux has-session -t '${SESSION}' 2>/dev/null && tmux send-keys -t '${SESSION}' '${key}' Enter || true"
}

send_to_all() {
  local key="$1"
  local skip_last="${2:-0}"   # how many nodes at the end to skip (default 0)

  local last=$((NUM_NODES - skip_last))
  if (( last < 1 )); then
    echo "send_to_all: skip_last=${skip_last} skips everything (NUM_NODES=${NUM_NODES})"
    return 0
  fi

  for ID in $(seq 1 "${last}") ; do
    send_to_one "$ID" "$key"
  done
}

# 1) send e everywhere
send_to_all "e"
sleep 1

# 2) then send l everywhere
send_to_all "l"

# Now: kill from the end (5,4,3,...) and after each kill:
#  - send e to all except last k nodes
#  - send l to node k
for ((k=1; k<=NUM_NODES-1; k++)); do
  kill_id=$((NUM_NODES - (k-1)))   # 5,4,3,...

  send_to_one "$kill_id" "kill"
  sleep 1

  send_to_all "e" "$k"             # skip last k
  sleep 1

  send_to_one "$k" "l"
done