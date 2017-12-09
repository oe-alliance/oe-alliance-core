#!/bin/sh
#
### BEGIN INIT INFO
# Provides:          dphys-swapfile
# Required-Start:    $syslog $remote_fs
# Required-Stop:     $syslog $remote_fs
# Should-Start:      $local_fs
# Should-Stop:       $local_fs
# Default-Start:     2 3 4 5
# Default-Stop:
# Short-Description: Autogenerate and use a swap file
# Description:       Autogenerate and use a swap file
### END INIT INFO

SWAPDEVS="/dev/mmcblk0p*"
swap_device=$(blkid -t PARTLABEL="swap" -o device ${SWAPDEVS} | head -n1)

if [ "${swap_device}" == "" ]; then
	echo "  Sorry, no swap drive found"
	exit 1
fi

inproc=$(cat /proc/swaps | grep -o ${swap_device} | head -n1)

grep -q ${swap_device} /etc/fstab
if [ $? -ne 0 ]; then
	mkswap ${swap_device}
	swapon ${swap_device}
	echo ${swap_device} 'swap swap defaults 0 0' >> /etc/fstab
elif [ "${inproc}" == "" ]; then
	mkswap ${swap_device}
	swapon ${swap_device}
fi
