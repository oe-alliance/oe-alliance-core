# avoid the console messages clobbering our logo
[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 0 > /sys/class/vtconsole/vtcon1/bind
# and set the correct videomode before showing the bootlogo
[ -f /etc/videomode ] && cat /etc/videomode > /proc/stb/video/videomode

if [ -f /usr/share/bootvideo.* ]; then
	BOOTVIDEO=/usr/share/`ls /usr/share/ | grep bootvideo*`
fi
if [ -f /media/usb/bootvideo.* ]; then
	BOOTVIDEO=/media/usb/`ls /media/usb/ | grep bootvideo*`
fi

if [ -f /usr/share/bootvideo.* ] || [ -f /usb/bootvideo.* ]; then
	echo bootvideo found @ $BOOTVIDEO
		if [ "$(grep config.audio.volume /etc/enigma2/settings)" ]; then
			vol=`grep config.audio.volume /etc/enigma2/settings | cut -d"=" -f2`
		else
			vol=30
		fi
		voldb=$((63-vol*63/100))
		if [ -e /proc/stb/avs/0/volume ]; then
			echo $voldb > /proc/stb/avs/0/volume
		fi
	#(sleep 25; killall gst-launch) & 
	#gst-launch playbin2 uri=file://$BOOTVIDEO &
	(sleep 11; killall eplayer4) &
	/usr/bin/eplayer4 $BOOTVIDEO
	else
	echo No flash bootlogo found, check /usr/share for your flashbootlogo, now showing original bootlogo
		if [ -x /usr/bin/showiframe ]; then
			if [ -f /etc/enigma2/bootlogo.mvi ]; then
				echo found /etc/enigma2/bootlogo.mvi
				/usr/bin/showiframe /etc/enigma2/bootlogo.mvi
			elif [ -f /usr/share/bootlogo.mvi.backup ]; then
				echo found /usr/share/bootlogo.mvi.backup
				/usr/bin/showiframe /usr/share/bootlogo.mvi.backup
			fi
		fi
fi