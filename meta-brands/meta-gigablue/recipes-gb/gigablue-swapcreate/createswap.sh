#!/bin/sh

device1=$(blkid -t  PARTLABEL="swap" -o device | head -n1)
swap1=$(cat /proc/swaps | grep -o "/dev/mmcblk0p10" | head -n1)

if [ "$device1" == "" ]; then
	echo "  Sorry, no swap drive found"
	exit 1
fi

grep -q "/dev/mmcblk0p10" /etc/fstab

if [ $? -ne 0 ]; then
	mkswap /dev/mmcblk0p10
	swapon /dev/mmcblk0p10
	echo '/dev/mmcblk0p10 none swap defaults 0 0' >> /etc/fstab
elif [ "$swap1" == "" ]; then
	mkswap /dev/mmcblk0p10
	swapon /dev/mmcblk0p10
fi
