# avoid the console messages clobbering our logo
[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 0 > /sys/class/vtconsole/vtcon1/bind
# and set the correct videomode before showing the bootlogo
[ -f /etc/videomode ] && cat /etc/videomode > /proc/stb/video/videomode
if [ "$(grep config.bootvideo.booting /etc/enigma2/settings)" ]; then
    BOOTVIDEO=`grep config.bootvideo.booting /etc/enigma2/settings | cut -d"=" -f2`
    gst-launch-1.0 playbin uri=file:///usr/share/enigma2/bootvideos/$BOOTVIDEO
    BOOTLOGO=/usr/share/bootlogo.mvi
    [ -f /etc/enigma2/bootlogo.mvi ] && BOOTLOGO=/etc/enigma2/bootlogo.mvi
    /usr/bin/showiframe ${BOOTLOGO}
    [ -f /etc/init.d/bootlogo.py ] && /usr/bin/python /etc/init.d/bootlogo.py
else
    BOOTLOGO=/usr/share/bootlogo.mvi
    [ -f /etc/enigma2/bootlogo.mvi ] && BOOTLOGO=/etc/enigma2/bootlogo.mvi
    /usr/bin/showiframe ${BOOTLOGO}
    [ -f /etc/init.d/bootlogo.py ] && /usr/bin/python /etc/init.d/bootlogo.py
fi

