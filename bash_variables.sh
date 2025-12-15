#!/usr/bin/env bash

# Where everything lives on local + remote machines
SEMWORK_HOMEDIR="/home"
CODE_SUBDIR="carvalho-roucairol"

git@github.com:jakubjanak2004/carvalho-roucairol.git

# Git
GIT_URL="git@github.com:jakubjanak2004/carvalho-roucairol.git"
GIT_BRANCH="master"

# Nodes
NUM_NODES=2
declare -a NODE_IP
NODE_IP[1]="192.168.1.101"
NODE_IP[2]="192.168.1.102"

# SSH login (CTU lab often uses dsv/dsv, your case is dsva)
REMOTE_USER="dsva"
DSV_PASS="dsva"

# Jar
FAT_JAR="semestralka.jar"
FAT_JAR_PATH="target"

# tmux session name prefix
SESSION_PREFIX="NODE"
