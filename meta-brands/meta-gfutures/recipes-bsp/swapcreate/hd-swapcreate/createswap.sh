#!/bin/sh

grep -q "/dev/mmcblk0p10" /etc/fstab

if [ $? -ne 0 ]; then
	mkswap /dev/mmcblk0p10
	swapon /dev/mmcblk0p10
	echo '/dev/mmcblk0p10 none swap defaults 0 0' >> /etc/fstab
fi