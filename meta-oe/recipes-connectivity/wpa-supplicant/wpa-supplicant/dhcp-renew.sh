#!/bin/sh
# wpa_cli action script, called as "<interface> <event>". udhcpc keeps its
# lease when wpa_supplicant moves to another network, so on every new
# association release it and ask again.

[ "$2" = "CONNECTED" ] || exit 0

PID=$(cat "/var/run/udhcpc.$1.pid" 2>/dev/null) || exit 0
[ "$(cat /proc/$PID/comm 2>/dev/null)" = "udhcpc" ] || exit 0
kill -USR2 "$PID"
kill -USR1 "$PID"
exit 0
