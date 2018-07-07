#!/bin/sh
# avoid the console messages clobbering our logo
[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 0 > /sys/class/vtconsole/vtcon1/bind
# and set the correct videomode before showing the bootlogo
[ -f /etc/videomode ] && cat /etc/videomode > /proc/stb/video/videomode

BOOTLOGO=/usr/share/bootlogo.mvi
BOOTVIDEO=/usr/share/bootvideo.mp4

if [ -f ${BOOTVIDEO} ] && [ "$(grep config.usage.show_bootvideo /etc/enigma2/settings)" == "config.usage.show_bootvideo=true" ]; then
	[ -f /etc/enigma2/bootvideo.mp4 ] && BOOTVIDEO=/etc/enigma2/bootvideo.mp4
	echo "1" > /proc/stb/lcd/mode
	echo 15 > /proc/stb/avs/0/volume
	echo "0" > /.go
	echo "bootlogo: Showing bootvideo..."
	/usr/bin/bootvideo ${BOOTVIDEO} &
else
	[ -f /etc/enigma2/bootlogo.mvi ] && BOOTLOGO=/etc/enigma2/bootlogo.mvi
	echo "bootlogo: Showing bootlogo..."
	/usr/bin/showiframe ${BOOTLOGO}
fi

exit 0
