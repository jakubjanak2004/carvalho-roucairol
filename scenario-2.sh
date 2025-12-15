#!/usr/bin/env bash
set -euo pipefail
source ./procedures.sh

# one instance enters the critical state first
send_to_one "5" "e"
sleep 1

# 3 other instances start running - they wait between the CS requests
send_to_one "1" "e"
sleep 1
send_to_one "2" "e"
sleep 1
send_to_one "3" "e"

# the first instance holding releases CS
sleep 1
send_to_one "5" "l"

# todo you have to release other nodes