#!/bin/sh

# Never wait for DHCP/WLAN inside a udev RUN worker. The detached worker and
# the networking init script share a lock because BusyBox ifupdown has a
# shared state file and must not start competing DHCP clients.
export PATH

case "$1" in
    --worker)
        # Coldplug events arrive before populate-volatile has created the
        # target of /var/run, where ifup, wpa_supplicant and udhcpc keep
        # their state; utmp is one of the files it puts there.
        wait=120
        while [ ! -e /var/run/utmp ] && [ "$wait" -gt 0 ]; do
            sleep 1
            wait=$((wait - 1))
        done
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

# The Broadcom driver picks the cell of an SSID itself. Where the bands differ
# in RSN, the handshake ends in IE_IN_4WAY_DIFFERS; one band leaves one cell.
bcm_pin_band() {
    [ -x /usr/bin/wl ] && [ -d "/tmp/bcm/$1" ] || return 0
    conf=/etc/wpa_supplicant.$1.conf
    [ -f "$conf" ] || return 0
    ssid=$(sed -n 's/^[[:space:]]*ssid="\(.*\)"$/\1/p' "$conf" | head -1)
    [ -n "$ssid" ] || return 0

    ifconfig "$1" up 2>/dev/null
    wl band auto >/dev/null 2>&1
    wl up >/dev/null 2>&1
    cells=$(iwlist "$1" scan 2>/dev/null | awk -v ssid="ESSID:\"$ssid\"" '
        BEGIN { RS = "Cell " }
        index($0, ssid) == 0 { next }
        {
            suites = "none"
            if (match($0, /Authentication Suites \([0-9]+\) : [^\n]*/))
                suites = substr($0, RSTART, RLENGTH)
            print suites
        }
    ' | sort -u | wc -l)

    [ "$cells" -gt 1 ] || return 0
    wl band b >/dev/null 2>&1
}

# Events can become stale while waiting for an earlier ifup/ifdown operation.
case "$ACTION" in
    add)
        [ -d "/sys/class/net/$INTERFACE" ] || exit 0
        if ! ip addr show dev "$INTERFACE" up | grep -q "$INTERFACE"; then
            bcm_pin_band "$INTERFACE"
            ifup "$INTERFACE"
        fi
        ;;
    remove)
        [ ! -d "/sys/class/net/$INTERFACE" ] || exit 0
        ifdown "$INTERFACE"
        ;;
esac
