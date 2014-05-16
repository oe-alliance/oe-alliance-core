# thoug not actually a bootlogo task, set the correct videomode before showing the bootlogo
cat /etc/videomode > /proc/stb/video/videomode

[ -e /etc/dropbear/dropbear_rsa_host_key ] &&  /usr/bin/showiframe /usr/share/bootlogo.mvi || /usr/bin/showiframe /usr/share/bootlogo_wait.mvi