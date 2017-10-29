#!/bin/sh
case "$1" in
start)
	echo -n "Starting udpxy: "
	start-stop-daemon -S -b -n udpxy -a /usr/bin/udpxy -- -p 4022
	echo "done"
	;;
stop)
	echo -n "Stoping udpxy: "
	start-stop-daemon -K -n udpxy
	echo "done"
	;;
restart)
	$0 stop
	$0 start
	;;
*)
	echo "Usage: udpxy { start | stop | restart }" >&2
	exit 1
	;;
esac
exit 0
