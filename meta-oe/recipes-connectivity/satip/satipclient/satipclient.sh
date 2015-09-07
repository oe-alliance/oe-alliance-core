#!/bin/sh

if ! [ -x /usr/bin/satipclient ]; then
	exit 0
fi

if [ `cat /proc/stb/info/chipset | grep '7358' ` ]; then
	sysctl -w net.core.rmem_max=26214400
fi

case "$1" in
	start)
		start-stop-daemon -S -b -x /usr/bin/satipclient
		;;
	stop)
		start-stop-daemon -K -x /usr/bin/satipclient
		;;
	restart|reload)
		$0 stop
		$0 start
		;;
	*)
		echo "Usage: $0 {start|stop|restart}"
		exit 1
		;;
esac

exit 0
