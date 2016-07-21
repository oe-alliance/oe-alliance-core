# avoid the console messages clobbering our logo
[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 0 > /sys/class/vtconsole/vtcon1/bind
# and set the correct videomode before showing the bootlogo
[ -f /etc/videomode ] && cat /etc/videomode > /proc/stb/video/videomode

BOOTLOGO=/usr/share/bootlogo.mvi
[ -f /etc/enigma2/bootlogo.mvi ] && BOOTLOGO=/etc/enigma2/bootlogo.mvi
/usr/bin/showiframe ${BOOTLOGO}
