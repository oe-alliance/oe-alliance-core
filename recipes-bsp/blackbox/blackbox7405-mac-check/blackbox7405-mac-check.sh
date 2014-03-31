#!/bin/sh

if ! [ -x /usr/bin/blackbox7405_mac_check ]; then
	exit 0
fi

case "$1" in
	start)
		/usr/bin/blackbox7405_mac_check
		;;
	stop)
		killall -9 usr/bin/blackbox7405_mac_check
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
