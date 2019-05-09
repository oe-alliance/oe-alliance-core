#!/bin/sh

if [ ! -f "/.resize-rootfs" ]; then
  if [ -f "/dev/block/by-name/rootfs" ]; then
    echo "Resizing rootfs, Do not unplug power!..."
    resize2fs /dev/block/by-name/rootfs
    touch "/.resize-rootfs"
  fi
fi

echo "dinobot start!!!"
cd /lib/modules/4.4.35/extra/
./load
