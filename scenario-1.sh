#!/usr/bin/env bash
set -euo pipefail
source ./bash_variables.sh
source ./procedures.sh

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

  send_to_all "l" "$k"
done

# start the node and then send e and l to all the alive nodes
# start the node and then send e and l to all the alive nodes (1..id)
for ((id=2; id<=NUM_NODES; id++)); do
  restart_node_from_tmux_history "$id"
  sleep 2

  skip_last=$((NUM_NODES - id))   # alive are 1..id
  send_to_all "e" "$skip_last"
  sleep 1
  send_to_all "l" "$skip_last"
done
