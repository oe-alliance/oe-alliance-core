#!/bin/sh

if [ ! "$(grep config.misc.firstrun /etc/enigma2/settings)" == "config.misc.firstrun=false" ]; then
	echo w | fdisk /dev/mmcblk0 
	partprobe 
fi



device1=$(blkid -t  PARTLABEL="swap" -o device | head -n1)
### /dev/mmcblk0p6 (2 partition) or /dev/mmcblk0p10 (4 partition) or /dev/mmcblk0p7 (new system)
swap1=$(cat /proc/swaps | grep -o "$device1" | head -n1)

if [ "$device1" == "" ]; then
	echo "  Sorry, no swap drive found"
	exit 1
fi

grep -q "$device1" /etc/fstab

if [ $? -ne 0 ]; then
	mkswap $device1
	swapon $device1
	echo "$device1 none swap defaults 0 0" >> /etc/fstab
elif [ "$swap1" == "" ]; then
	mkswap $device1
	swapon $device1
fi

