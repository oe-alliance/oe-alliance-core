#!/bin/sh
#
# start/stop inadyn-mt 

if ! [ -x /usr/bin/inadyn-mt ]; then
	exit 0
fi

case "$1" in
	start)
		start-stop-daemon -S -b -x /usr/bin/inadyn-mt
		;;
	stop)
		start-stop-daemon -K -x /usr/bin/inadyn-mt
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

