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

  sshpass -p "${DSV_PASS}" ssh -o StrictHostKeyChecking=no "${REMOTE_USER}@${NODE}" -- \
    "tmux kill-session -t '${SESSION}' 2>/dev/null || true; tmux new-session -d -s '${SESSION}'"

  sshpass -p "${DSV_PASS}" ssh -o StrictHostKeyChecking=no "${REMOTE_USER}@${NODE}" -- \
    "tmux send -t '${SESSION}' 'cd \"${REMOTE_NODE_DIR}\" && java -jar \"${FAT_JAR}\"' ENTER"
done






##!/usr/bin/env bash
#set -euo pipefail
#
## ====== CONFIG ======
#HOSTS=("192.168.1.101" "192.168.1.102")
#JAR_LOCAL="target/semestralka.jar"
#REMOTE_USER=dsva
#REMOTE_DIR=/opt/semestralka
#
## recompile the .jar
#mvn clean package
#
#REMOTE_USER="${REMOTE_USER:-$USER}"       # override: REMOTE_USER=jakub ./deploy.sh
#REMOTE_DIR="${REMOTE_DIR:-~/semestralka}"
#REMOTE_JAR="$REMOTE_DIR/semestralka.jar"
#
#SSH_OPTS="-o StrictHostKeyChecking=accept-new -o LogLevel=ERROR"
#
#if [[ ! -f "$JAR_LOCAL" ]]; then
#  echo "ERROR: '$JAR_LOCAL' not found. Build it first (mvn clean package)."
#  exit 1
#fi
#
#for host in "${HOSTS[@]}"; do
#  ssh $SSH_OPTS "${REMOTE_USER}@${host}" "mkdir -p '$REMOTE_DIR'" >/dev/null 2>&1
#  scp $SSH_OPTS "$JAR_LOCAL" "${REMOTE_USER}@${host}":"$REMOTE_JAR" >/dev/null 2>&1
#
#  ssh $SSH_OPTS "${REMOTE_USER}@${host}" bash -lc "'
#    set -e
#    mkdir -p \"$REMOTE_DIR\"
#
#    # stop previous started instance (if any)
#    if [[ -f \"$REMOTE_PID\" ]]; then
#      pid=\$(cat \"$REMOTE_PID\" || true)
#      if [[ -n \"\$pid\" ]] && kill -0 \"\$pid\" 2>/dev/null; then
#        kill \"\$pid\" 2>/dev/null || true
#        sleep 1
#        kill -9 \"\$pid\" 2>/dev/null || true
#      fi
#      rm -f \"$REMOTE_PID\"
#    fi
#
#    # start immediately
#    cd \"$REMOTE_DIR\"
#    nohup java -jar \"$REMOTE_JAR\" > \"$REMOTE_LOG\" 2>&1 &
#    echo \$! > \"$REMOTE_PID\"
#  '" >/dev/null 2>&1
#done
