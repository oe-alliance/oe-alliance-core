#!/bin/sh

ACTION=$1
DEVNAME=$2
LABEL=$3

LINK_DIR="/dev/block/by-name"

mkdir -p "$LINK_DIR"

case "$ACTION" in
  add)
    case "$LABEL" in
      startup|rootfs)
        ln -sf "$DEVNAME" "$LINK_DIR/$LABEL"
        ;;
    esac
    ;;
  remove)
    for name in startup rootfs; do
      if [ "$(readlink -f "$LINK_DIR/$name")" = "$DEVNAME" ]; then
        rm -f "$LINK_DIR/$name"
      fi
    done
    ;;
esac
