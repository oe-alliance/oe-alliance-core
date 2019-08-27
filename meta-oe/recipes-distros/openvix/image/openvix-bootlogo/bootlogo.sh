#!/bin/sh
# avoid the console messages clobbering our logo
[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 0 > /sys/class/vtconsole/vtcon1/bind
# and set the correct videomode before showing the bootlogo
[ -f /etc/videomode ] && cat /etc/videomode > /proc/stb/video/videomode

if [ -f /etc/enigma2/bootlogo.mvi ]; then 
	/usr/bin/showiframe /etc/enigma2/bootlogo.mvi
else
	skin=`sed -En 's|config\.skin\.primary_skin=(.+)/skin\.xml|\1|p' /etc/enigma2/settings`
	if [ z $skin ]; then
		skin=`strings -n 10 /usr/lib/enigma2/python/skin.pyo | egrep -o -m 1 ".+/skin.xml" | sed 's|/skin.xml.*||'`
	fi
	if [ -n $skin -a -f /usr/share/enigma2/$skin/bootlogo.mvi ]; then
		/usr/bin/showiframe /usr/share/enigma2/$skin/bootlogo.mvi
	else
		/usr/bin/showiframe /usr/share/bootlogo.mvi
    fi
fi

[ -f /etc/init.d/bootlogo.py ] && /usr/bin/python /etc/init.d/bootlogo.py
/bin/true
