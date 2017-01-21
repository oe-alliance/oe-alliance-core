#! /bin/sh
#
# wowl
#
# Version:      @(#)wowl  1.00  10-Jul-2016  
#

PATH=/sbin:/bin:/usr/sbin:/usr/bin

#activate WOWL
grep -q 'enable' /proc/stb/fp/wol
if [ $? -eq 0 ]
then
	echo [WOWL] Activaing WOWL...
	count=1
	while [ $count -le 3 ] ; do
		let count=$count+1
		WLAN3STATE=$(cat /sys/class/net/wlan3/operstate)
		echo $WLAN3STATE
		if [ "$WLAN3STATE" == "up" ]; then
			break
		else
			echo "Bring up WiFi interface wlan3"
			ifup wlan3
		fi
		sleep 1.0
	done
	wl wowl 1
	wl wowl_activate
	mknod /dev/wake0 c 34 0
	echo enable > /proc/stb/fp/forcewol
	sleep	1
else
    echo [WOL] WakeOnLAN is not enabled
fi


: exit 0