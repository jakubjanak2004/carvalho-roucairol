#!/usr/bin/env bash

# Where everything lives on local + remote machines
SEMWORK_HOMEDIR="/home/dsva/Desktop"
CODE_SUBDIR="carvalho-roucairol"

# Git
GIT_URL="git@github.com:jakubjanak2004/carvalho-roucairol.git"
GIT_BRANCH="master"

# Nodes
NUM_NODES=5
declare -a NODE_IP
NODE_IP[1]="192.168.1.100"
NODE_IP[2]="192.168.1.101"
NODE_IP[3]="192.168.1.102"
NODE_IP[4]="192.168.1.101"
NODE_IP[5]="192.168.1.102"

declare -a NODE_PORT
NODE_PORT[1]="12345"
NODE_PORT[2]="12345"
NODE_PORT[3]="12345"
NODE_PORT[4]="12400"
NODE_PORT[5]="12400"

# SSH login (CTU lab often uses dsv/dsv, your case is dsva)
REMOTE_USER="dsva"
DSV_PASS="dsva"

# Jar
FAT_JAR="semestralka.jar"
FAT_JAR_PATH="target"

# tmux session name prefix
SESSION_PREFIX="NODE"
