#!/bin/sh

# Never wait for DHCP/WLAN inside a udev RUN worker. The detached worker and
# the networking init script share a lock because BusyBox ifupdown has a
# shared state file and must not start competing DHCP clients.
export PATH

case "$1" in
    --worker)
        exec flock --close --wait 180 /run/oe-network.lock "$0" --locked "$2" "$3"
        ;;
    --locked)
        ACTION=$2
        INTERFACE=$3
        ;;
esac

case "$ACTION" in add|remove) ;; *) exit 0 ;; esac
case "$INTERFACE" in
    ''|-*|wifi*|*[!a-zA-Z0-9_.:-]*) exit 0 ;;
esac

# Retain the existing policy: only interfaces configured in interfaces.
awk -v iface="$INTERFACE" '
    $1 == "iface" && $2 == iface { found = 1; exit }
    END { exit !found }
' /etc/network/interfaces || exit 0

if [ "$1" != --locked ]; then
    start-stop-daemon --start --background --exec "$0" -- --worker "$ACTION" "$INTERFACE"
    exit $?
fi

# Events can become stale while waiting for an earlier ifup/ifdown operation.
case "$ACTION" in
    add)
        [ -d "/sys/class/net/$INTERFACE" ] || exit 0
        ip addr show dev "$INTERFACE" up | grep -q "$INTERFACE" || ifup "$INTERFACE"
        ;;
    remove)
        [ ! -d "/sys/class/net/$INTERFACE" ] || exit 0
        ifdown "$INTERFACE"
        ;;
esac
