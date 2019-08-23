# avoid the console messages clobbering our logo
[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 0 > /sys/class/vtconsole/vtcon1/bind
# and set the correct videomode before showing the bootlogo
[ -f /etc/videomode ] && cat /etc/videomode > /proc/stb/video/videomode

BOOTLOGO=/usr/share/bootlogo.mvi
[ -f /etc/enigma2/bootlogo.mvi ] && BOOTLOGO=/etc/enigma2/bootlogo.mvi
skin=`sed -En 's|config\.skin\.primary_skin=(.+)/skin\.xml|\1|p' /etc/enigma2/settings`
[ -z $skin ] && skin=`strings -n 10 /usr/lib/enigma2/python/skin.pyo | egrep -o -m 1 ".+/skin.xml" | sed 's|/skin.xml.*||'`
[ -n $skin  -a -f /usr/share/enigma2/$skin/bootlogo.mvi ] && BOOTLOGO=/usr/share/enigma2/$skin/bootlogo.mvi
/usr/bin/showiframe ${BOOTLOGO}

[ -f /etc/init.d/bootlogo.py ] && /usr/bin/python /etc/init.d/bootlogo.py
