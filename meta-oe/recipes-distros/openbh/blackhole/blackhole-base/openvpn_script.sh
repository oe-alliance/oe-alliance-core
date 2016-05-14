#!/bin/sh -e
#
# Original version by Robert Leslie
# <rob@mars.org>, edited by iwj and cs
# Modified for openvpn by Alberto Gonzalez Iniesta <agi@inittab.org>
# Modified for restarting / starting / stopping single tunnels by Richard Mueller <mueller@teamix.net>

test $DEBIAN_SCRIPT_DEBUG && set -v -x

DAEMON=/usr/sbin/openvpn
DESC="virtual private network daemon"
CONFIG_DIR=/etc/openvpn
test -x $DAEMON || exit 0
test -d $CONFIG_DIR || exit 0

# Source defaults file; edit that file to configure this script. AUROSTART = all - none
AUTOSTART="none"
STATUSREFRESH=10
if test -e /etc/default/openvpn ; then
  . /etc/default/openvpn
fi

start_vpn () {
    if grep -q '^[	 ]*daemon' $CONFIG_DIR/$NAME.conf ; then
      # daemon already given in config file
      DAEMONARG=
    else
      # need to daemonize
      DAEMONARG="--daemon ovpn-$NAME"
    fi

    if grep -q '^[	 ]*status ' $CONFIG_DIR/$NAME.conf ; then
      # status file already given in config file
      STATUSARG=""
    elif test $STATUSREFRESH -eq 0 ; then
      # default status file disabled in /etc/default/openvpn
      STATUSARG=""
    else
      # prepare default status file
      STATUSARG="--status /var/run/openvpn.$NAME.status $STATUSREFRESH"
    fi

    echo -n " $NAME"
    STATUS="OK"

    $DAEMON --writepid /var/run/openvpn.$NAME.pid \
            $DAEMONARG $STATUSARG --cd $CONFIG_DIR \
            --config $CONFIG_DIR/$NAME.conf < /dev/null || STATUS="FAILED"
    echo -n "($STATUS)"
}
stop_vpn () {
  kill `cat $PIDFILE` || true
  rm $PIDFILE
  rm -f /var/run/openvpn.$NAME.status 2> /dev/null
}

case "$1" in
start)
  echo -n "Starting $DESC:"

  # autostart VPNs
  if test -z "$2" ; then
    # check if automatic startup is disabled by AUTOSTART=none
    if test "x$AUTOSTART" = "xnone" -o -z "$AUTOSTART" ; then
      echo " Autostart disabled."
      exit 0
    fi
    if test -z "$AUTOSTART" -o "x$AUTOSTART" = "xall" ; then
      # all VPNs shall be started automatically
      for CONFIG in `cd $CONFIG_DIR; ls *.conf 2> /dev/null`; do
        NAME=${CONFIG%%.conf}
        start_vpn
      done
    else
      # start only specified VPNs
      for NAME in $AUTOSTART ; do
        if test -e $CONFIG_DIR/$NAME.conf ; then
          start_vpn
        else
          echo -n " (failure: No such VPN: $NAME)"
        fi
      done
    fi
  #start VPNs from command line
  else
    while shift ; do
      [ -z "$1" ] && break
      if test -e $CONFIG_DIR/$1.conf ; then
        NAME=$1
        start_vpn
      else
        echo -n " (failure: No such VPN: $1)"
      fi
    done
  fi
  echo "."

  ;;
stop)
  echo -n "Stopping $DESC:"

  if test -z "$2" ; then
    for PIDFILE in `ls /var/run/openvpn.*.pid 2> /dev/null`; do
      NAME=`echo $PIDFILE`
      NAME=${NAME%%.pid}
      stop_vpn
      echo -n " $NAME"
    done
  else
    while shift ; do
      [ -z "$1" ] && break
      if test -e /var/run/openvpn.$1.pid ; then
        PIDFILE=`ls /var/run/openvpn.$1.pid 2> /dev/null`
        NAME=`echo $PIDFILE`
        NAME=${NAME%%.pid}
        stop_vpn
        echo -n " $NAME"
      else
        echo -n " (failure: No such VPN is running: $1)"
      fi
    done
  fi
  echo "."
  ;;
# We only 'reload' for running VPNs. New ones will only start with 'start' or 'restart'.
reload|force-reload)
  echo -n "Reloading $DESC:"
  for PIDFILE in `ls /var/run/openvpn.*.pid 2> /dev/null`; do
    NAME=`echo $PIDFILE`
    NAME=${NAME%%.pid}
# If openvpn if running under a different user than root we'll need to restart
    if egrep '^( |\t)*user' $CONFIG_DIR/$NAME.conf > /dev/null 2>&1 ; then
      stop_vpn
      sleep 1
      start_vpn
      echo -n "(restarted)"
    else
      kill -HUP `cat $PIDFILE` || true
    echo -n " $NAME"
    fi
  done
  echo "."
  ;;

restart)
  shift
  $0 stop ${@}
  sleep 1
  $0 start ${@}
  ;;
cond-restart)
  echo -n "Restarting $DESC:"
  for PIDFILE in `ls /var/run/openvpn.*.pid 2> /dev/null`; do
    NAME=`echo $PIDFILE `
    NAME=${NAME%%.pid}
    stop_vpn
    sleep 1
    start_vpn
  done
  echo "."
  ;;
*)
  echo "Usage: $0 {start|stop|reload|restart|force-reload|cond-restart}" >&2
  exit 1
  ;;
esac

exit 0

# vim:set ai sts=2 sw=2 tw=0:
