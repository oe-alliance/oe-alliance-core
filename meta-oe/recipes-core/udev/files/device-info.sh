#!/bin/sh

ACTION=$1
DEV=$2
UUID=$3
FSTYPE=$4
FSTYPE_DIR="/dev/fstype"
UUID_DIR="/dev/uuid"
BASENAME=$(basename "$DEV")
UUID_FILE="$UUID_DIR/$BASENAME"
FSTYPE_FILE="$FSTYPE_DIR/$BASENAME"

case "$ACTION" in
  add|change)
    mkdir -p "$FSTYPE_DIR"
    mkdir -p "$UUID_DIR"
    if [ -n "$UUID" ]; then
      echo "$UUID" > "$UUID_FILE"
    fi
    if [ -n "$FSTYPE" ]; then
      echo "$FSTYPE" > "$FSTYPE_FILE"
    fi
    ;;
  remove)
    if [ -f "$UUID_FILE" ]; then
      rm -f "$UUID_FILE"
    fi
    if [ -f "$FSTYPE_FILE" ]; then
      rm -f "$FSTYPE_FILE"
    fi
    ;;
esac
