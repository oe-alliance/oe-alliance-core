#!/bin/sh
function search {
for i in $1;
do
  if [ "$i" != "$1" ]; then
    partname=`cat /$i/uevent | grep PARTNAME | cut -d '=' -f 2`
    devname=`cat /$i/uevent | grep DEVNAME | cut -d '=' -f 2`
    mkdir -p /dev/block/by-name/
    ln -sf /dev/$devname /dev/block/by-name/$partname
  fi
done
}

if [ -d "/sys/block/mmcblk0" ]; then
    search "/sys/block/mmcblk0/mmcblk0p*"
fi
if [ -d "/sys/block/mmcblk1" ]; then
    search "/sys/block/mmcblk1/mmcblk1p*"
fi
if [ -d "/sys/block/sda" ]; then
    search "/sys/block/sda/sda*"
fi
