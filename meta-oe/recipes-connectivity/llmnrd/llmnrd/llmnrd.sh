#!/bin/sh

### BEGIN INIT INFO
# Provides:          llmnrd
# Required-Start:    $network $local_fs
# Required-Stop:     $network $local_fs
# X-Start-Before:    smbd
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: start Link Local Multicast Name Resolution (LLMNR) daemon
### END INIT INFO

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

