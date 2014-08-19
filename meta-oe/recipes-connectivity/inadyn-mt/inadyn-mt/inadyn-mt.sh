#!/bin/sh
#
# start/stop inadyn-mt
DAEMON=/usr/bin/inadyn-mt
NAME=inadyn-mt
ARGS="--log_file /var/log/inadyn.log --input_file /etc/inadyn.conf"

if ! [ -x /usr/bin/inadyn-mt ]; then
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

