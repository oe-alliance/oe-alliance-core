#!/bin/sh

ACTION=$1
DEV=$2
UUID=$3
FSTYPE=$4
SIZE=$5
FSTYPE_DIR="/dev/fstype"
UUID_DIR="/dev/uuid"
SIZE_DIR="/dev/size"
BASENAME=$(basename "$DEV")
DEVBASE=${BASENAME:0:7}
if [ ! -d /sys/block/${DEVBASE} ]; then
	DEVBASE=${BASENAME:0:3}
fi
UUID_FILE="$UUID_DIR/$BASENAME"
FSTYPE_FILE="$FSTYPE_DIR/$BASENAME"
SIZE_FILE="$SIZE_DIR/$BASENAME"

if [ -z "$SIZE" ]; then
	if [ -f "/sys/block/$DEVBASE/$BASENAME/size" ]; then
		SIZE=$(cat "/sys/block/$DEVBASE/$BASENAME/size")
	elif [ -f "/sys/block/$DEVBASE/size" ]; then
		SIZE=$(cat "/sys/block/$DEVBASE/size")
	fi
fi

case "$ACTION" in
  add|change)
    mkdir -p "$FSTYPE_DIR"
    mkdir -p "$UUID_DIR"
    mkdir -p "$SIZE_DIR"
    if [ -n "$UUID" ]; then
      echo "$UUID" > "$UUID_FILE"
    fi
    if [ -n "$FSTYPE" ]; then
      echo "$FSTYPE" > "$FSTYPE_FILE"
    fi
    if [ -n "$SIZE" ]; then
      echo "$SIZE" > "$SIZE_FILE"
    fi
    ;;
  remove)
    if [ -f "$UUID_FILE" ]; then
      rm -f "$UUID_FILE"
    fi
    if [ -f "$FSTYPE_FILE" ]; then
      rm -f "$FSTYPE_FILE"
    fi
    if [ -f "$SIZE_FILE" ]; then
      rm -f "$SIZE_FILE"
    fi
    ;;
esac