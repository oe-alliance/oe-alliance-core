#!/bin/sh
function search {
for i in $1;
do
  if [ "$i" != "$1" ]; then
    partname=`cat /$i/uevent | grep PARTNAME | cut -d '=' -f 2`
    devname=`cat /$i/uevent | grep DEVNAME | cut -d '=' -f 2`
    mkdir -p /dev/block/by-name/
    if [ ! -e /dev/block/by-name/$partname ]; then
      ln -sf /dev/$devname /dev/block/by-name/$partname
    else
      ln -sf /dev/$devname /dev/block/by-name/$partname-$devname
    fi
    if [ -d "/usr/share/u-boot-bin/" ] && [ -e "/dev/mmcblk0p7" ]; then
      mkdir -p /dev/disk/by-label/
      mkdir -p /dev/disk/by-partlabel/
      ln -sf /dev/$devname /dev/disk/by-label/$partname
      ln -sf /dev/$devname /dev/disk/by-partlabel/$partname
      if [ ! -e /dev/$partname ]; then
        ln -sf /dev/$devname c$partname
      fi
    fi
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
if [ -e "/dev/block/by-name/flag" ]; then
    if [ ! -e /dev/block/by-name/bootoptions ]; then
      ln -sf /boot/ /dev/block/by-name/bootoptions
    fi
fi

if [ -e "/sys/block/mtdblock2/device/name" ]; then
    devname=`cat /sys/block/mtdblock2/device/name`
    if [ $devname == "bootoptions" ]; then
      mkdir -p /dev/block/by-name/
      ln -sf /dev/mtdblock2 /dev/block/by-name/$devname
    fi
fi
