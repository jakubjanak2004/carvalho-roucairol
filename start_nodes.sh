#!/usr/bin/env bash
set -euo pipefail

# ====== CONFIG ======
HOSTS=("192.168.1.101" "192.168.1.102")
JAR_LOCAL="target/semestralka.jar"
REMOTE_USER=dsva
REMOTE_DIR=/opt/semestralka

# recompile the .jar
mvn clean package

REMOTE_USER="${REMOTE_USER:-$USER}"       # override: REMOTE_USER=jakub ./deploy.sh
REMOTE_DIR="${REMOTE_DIR:-~/semestralka}"
REMOTE_JAR="$REMOTE_DIR/semestralka.jar"
REMOTE_LOG="$REMOTE_DIR/app.log"
REMOTE_PID="$REMOTE_DIR/app.pid"

SSH_OPTS="-o StrictHostKeyChecking=accept-new -o LogLevel=ERROR"

if [[ ! -f "$JAR_LOCAL" ]]; then
  echo "ERROR: '$JAR_LOCAL' not found. Build it first (mvn clean package)."
  exit 1
fi

for host in "${HOSTS[@]}"; do
  ssh $SSH_OPTS "${REMOTE_USER}@${host}" "mkdir -p '$REMOTE_DIR'" >/dev/null 2>&1
  scp $SSH_OPTS "$JAR_LOCAL" "${REMOTE_USER}@${host}":"$REMOTE_JAR" >/dev/null 2>&1

  ssh $SSH_OPTS "${REMOTE_USER}@${host}" bash -lc "'
    set -e
    mkdir -p \"$REMOTE_DIR\"

    # stop previous started instance (if any)
    if [[ -f \"$REMOTE_PID\" ]]; then
      pid=\$(cat \"$REMOTE_PID\" || true)
      if [[ -n \"\$pid\" ]] && kill -0 \"\$pid\" 2>/dev/null; then
        kill \"\$pid\" 2>/dev/null || true
        sleep 1
        kill -9 \"\$pid\" 2>/dev/null || true
      fi
      rm -f \"$REMOTE_PID\"
    fi

    # start immediately
    cd \"$REMOTE_DIR\"
    nohup java -jar \"$REMOTE_JAR\" > \"$REMOTE_LOG\" 2>&1 &
    echo \$! > \"$REMOTE_PID\"
  '" >/dev/null 2>&1
done
