#!/bin/sh
# avoid the console messages clobbering our logo
[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 0 > /sys/class/vtconsole/vtcon1/bind
# and set the correct videomode before showing the bootlogo
[ -f /etc/videomode ] && cat /etc/videomode > /proc/stb/video/videomode

if [ -x /usr/bin/showiframe ]; then
	SKIN=`sed -En 's|config\.skin\.primary_skin=(.+)/skin\.xml|\1|p' /etc/enigma2/enigma2/settings`
	if [ -z "$SKIN" ]; then
		SKIN=`strings -n 10 /usr/lib/enigma2/python/skin.pyc | egrep -o -m 1 ".+/skin.xml" | sed 's|/skin.xml.*||'`
	fi
	if [ -n "${SKIN}" ]; then
		SEARCHDIRS="/etc/enigma2/${SKIN} /etc/enigma2/skin_common /etc/enigma2 /usr/share/enigma2/${SKIN} /usr/share/enigma2/skin_default /usr/share/enigma2 /usr/share"
	else
		SEARCHDIRS="/etc/enigma2/skin_common /etc/enigma2 /usr/share/enigma2/skin_default /usr/share/enigma2 /usr/share"
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
/bin/true
