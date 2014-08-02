# thoug not actually a bootlogo task, set the correct videomode before showing the bootlogo
cat /etc/videomode > /proc/stb/video/videomode

[ -e /etc/dropbear/dropbear_rsa_host_key ]

#&&  /usr/bin/showiframe /usr/share/bootlogo.mvi || /usr/bin/showiframe /usr/share/bootlogo_wait.mvi

if [ -x /usr/bin/showiframe ]; then
	if [ -f /etc/enigma2/bootlogo.mvi ]; then
		echo found /etc/enigma2/bootlogo.mvi
		/usr/bin/showiframe /etc/enigma2/bootlogo.mvi
	elif [ -f /usr/share/bootlogo.mvi ]; then
		echo found /usr/share/bootlogo.mvi
		/usr/bin/showiframe /usr/share/bootlogo.mvi
	fi
fi

/etc/init.d/bootvideo &
