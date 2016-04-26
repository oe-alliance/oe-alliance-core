#!/bin/sh
DAEMON=/usr/bin/inadyn
NAME=inadyn
DESC="InaDyn dynamic DNS Client"
INADYN_ON=0
INADYN_USERNAME=myusername@gmail.com
INADYN_PASSWORD=mypassword
INADYN_ALIAS=me@dyndns.org
UPDATE_PERIOD=3600000
LOG_FILE_ON=1
LOG_NAME=/var/log/inadyn.log
DYN_SYSTEM_ON=1
DYN_SYSTEM=dyndns@dyndns.org


test -f $DAEMON || exit 0

set -e

case "$1" in
    start)
	if [ $INADYN_ON -ne 0 ]; then
		ARGS="-u $INADYN_USERNAME -p $INADYN_PASSWORD -a $INADYN_ALIAS --update_period $UPDATE_PERIOD"	
		if [ $LOG_FILE_ON = 1 ]; then
			ARGS="$ARGS --log_file $LOG_NAME"
		fi
		if [ $LOG_FILE_ON = 2 ]; then
			ARGS="$ARGS --syslog"
		fi
		if [ $DYN_SYSTEM_ON -ne 0 ]; then
			ARGS="$ARGS --dyndns_system $DYN_SYSTEM"

		fi
		echo -n "starting $DESC: $NAME... "
		start-stop-daemon -S -b -n $NAME -a $DAEMON -- $ARGS
		echo "done."
	fi
	;;
    stop)
        echo -n "stopping $DESC: $NAME... "
	start-stop-daemon -K -n $NAME
	rm $LOG_NAME
	echo "done."
	;;
    restart)
        echo "restarting $DESC: $NAME... "
 	$0 stop
	$0 start
	echo "done."
	;;
    reload)
    	echo -n "reloading $DESC: $NAME... "
    	killall -HUP $(basename ${DAEMON})
	echo "done."
	;;
    *)
	echo "Usage: $0 {start|stop|restart|reload}"
	exit 1
	;;
esac

exit 0

