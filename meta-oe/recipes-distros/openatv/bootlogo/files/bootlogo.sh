#!/bin/sh

if [ "$1x" == "stopx" ]
then
	exit 0
fi

# Avoid the console messages clobbering our logo.
[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 0 > /sys/class/vtconsole/vtcon1/bind
# Set the correct videomode before showing the bootlogo.
[ -f /etc/videomode ]  && [ -f /proc/stb/video/videomode ] && cat /etc/videomode > /proc/stb/video/videomode

if [ -x /usr/bin/showiframe ]; then
	SKIN=`sed -En 's|config\.skin\.primary_skin=(.+)/skin\.xml|\1|p' /etc/enigma2/settings`
	if [ -n "${SKIN}" ]; then
		SEARCHDIRS="/etc/enigma2/${SKIN} /etc/enigma2/skin_common /etc/enigma2 /usr/share/enigma2/${SKIN} /usr/share/enigma2/skin_default /usr/share /usr/share/enigma2"
	else
		SEARCHDIRS="/etc/enigma2/skin_common /etc/enigma2 /usr/share/enigma2/skin_default /usr/share /usr/share/enigma2"
	fi
	for DIR in ${SEARCHDIRS}; do
		if [ -d "${DIR}" ] && [ -f "${DIR}/bootlogo.mvi" ]; then
			/usr/bin/showiframe "${DIR}/bootlogo.mvi" &
			break
		fi
	done
fi

[ -f /etc/init.d/bootlogo.py ] && /usr/bin/python /etc/init.d/bootlogo.py
[ -f /usr/share/lcd.png ] && /usr/bin/displayvfd -p /usr/share/lcd.png
