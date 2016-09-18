#!/bin/sh

case "$1" in
	start)
		# avoid the console messages clobbering our logo
		[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 0 > /sys/class/vtconsole/vtcon1/bind
		# and set the correct videomode before showing the bootlogo
		[ -f /etc/videomode ] && cat /etc/videomode > /proc/stb/video/videomode

		BOOTLOGO=/usr/share/bootlogo.mvi
		[ -f /etc/enigma2/bootlogo.mvi ] && BOOTLOGO=/etc/enigma2/bootlogo.mvi
		/usr/bin/showiframe ${BOOTLOGO}
		#[ -f /etc/init.d/bootlogo2.py ] && /usr/bin/python /etc/init.d/bootlogo2.py &
		[ -f /etc/init.d/bootlogo.py ] && /usr/bin/python /etc/init.d/bootlogo.py
	;;
	stop)
		killall -9 showiframe
	;;
	*)
		echo "Usage: $0 {start|stop}"
		exit 1
	;;
	esac

exit 0



