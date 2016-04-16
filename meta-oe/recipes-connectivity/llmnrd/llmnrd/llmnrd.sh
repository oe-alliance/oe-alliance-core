#!/bin/sh
#
# start/stop llmnrd
DAEMON=/usr/bin/llmnrd
NAME=llmnrd
ARGS="-6 -d"

if ! [ -x $DAEMON ]; then
	exit 0
fi

case "$1" in
	start)
		start-stop-daemon -S -b -n $NAME -a $DAEMON -- $ARGS
		;;
	stop)
		start-stop-daemon -K -n $NAME -a $DAEMON -- $ARGS
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

