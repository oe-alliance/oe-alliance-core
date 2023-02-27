#!/bin/sh
eval $(grep ^IFACE /etc/udev/bcmwifi_drv.sh)

if [ -e /sys/class/net/${IFACE} ]; then
	exit 0
fi

count=0
stop=50
while true; do
	A=`lsusb |grep bd27`
	if [ x"$A" == x ]; then
		count=$(($count+1))
#		echo "checking wifi.. $count"
		usleep 100000
		if [ $count -ge $stop ]; then
			break
		fi
	else
		echo "wifi found"
		break
	fi
done

/etc/udev/bcmwifi_firmware.sh
sleep 0.1
/etc/udev/bcmwifi_drv.sh

