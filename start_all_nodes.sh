#!/usr/bin/env bash
set -euo pipefail

source ./bash_variables.sh

# --- Only git pull (no clone) ---
CODE_DIR="${SEMWORK_HOMEDIR}/${CODE_SUBDIR}"
if [ ! -d "${CODE_DIR}/.git" ]; then
  echo "ERROR: ${CODE_DIR} is not a git repo. Put the repo there first."
  exit 1
fi

cd "${CODE_DIR}"
git checkout "${GIT_BRANCH}"
git pull

# --- Build ---
mvn -q clean package

LOCAL_JAR="${FAT_JAR_PATH}/${FAT_JAR}"
if [ ! -f "${LOCAL_JAR}" ]; then
  echo "ERROR: Jar not found: ${LOCAL_JAR}"
  exit 1
fi

# --- Upload + start on nodes ---
for ID in $(seq 1 "${NUM_NODES}") ; do
  NODE="${NODE_IP[$ID]}"
  REMOTE_NODE_DIR="${SEMWORK_HOMEDIR}/NODE_${ID}"
  SESSION="${SESSION_PREFIX}_${ID}"

  sshpass -p "${DSV_PASS}" ssh -o StrictHostKeyChecking=no "${REMOTE_USER}@${NODE}" \
    "mkdir -p '${REMOTE_NODE_DIR}'"

  sshpass -p "${DSV_PASS}" scp -o StrictHostKeyChecking=no "${LOCAL_JAR}" \
    "${REMOTE_USER}@${NODE}:${REMOTE_NODE_DIR}/${FAT_JAR}"

  # create tmux session only if it doesn't exist
  sshpass -p "${DSV_PASS}" ssh -o StrictHostKeyChecking=no "${REMOTE_USER}@${NODE}" -- \
    "tmux has-session -t '${SESSION}' 2>/dev/null || tmux new-session -d -s '${SESSION}'"

  # start jar only if not already running in that session
  sshpass -p "${DSV_PASS}" ssh -o StrictHostKeyChecking=no "${REMOTE_USER}@${NODE}" -- \
    "tmux capture-pane -pt '${SESSION}' -S -200 | grep -q '${FAT_JAR} NODE_${ID}' || \
     tmux send -t '${SESSION}' 'cd \"${REMOTE_NODE_DIR}\" && java -jar \"${FAT_JAR}\" NODE_${ID} ${NODE} ${NODE_PORT[$ID]} ${NODE_IP[0]} ${NODE_PORT[0]}' ENTER"

    sleep 5
done
