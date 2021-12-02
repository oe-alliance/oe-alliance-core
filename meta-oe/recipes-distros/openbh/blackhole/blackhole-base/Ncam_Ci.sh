#!/bin/sh

CAMNAME="Common Interface"

remove_tmp () {
	rm -rf /tmp/*.info /tmp/*.tmp /tmp/.oscam* /tmp/.ncam* /tmp/.cccam* /tmp/.CCcam* /tmp/*mgcamd*
}

case "$1" in
	start)
	echo "[SCRIPT] $1: $CAMNAME"
	remove_tmp
	ulimit -s 1024
	exec start-stop-daemon -S -x /usr/bin/vucamd
	;;
	stop)
	echo "[SCRIPT] $1: $CAMNAME"
	remove_tmp
	exec start-stop-daemon -K -R 2 -x /usr/bin/vucamd
	;;
	*)
	$0 stop
	exit 0
	;;
esac

exit 0








