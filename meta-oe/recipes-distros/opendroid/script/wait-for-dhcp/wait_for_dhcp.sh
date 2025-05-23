#!/bin/sh

log_file="/home/root/logs/network.log"
mkdir -p "$(dirname "$log_file")"
echo "" > "$log_file"

log_message() {
    echo "$(date '+%Y-%m-%d %H:%M:%S'): $1" >> "$log_file"
}

check_ipv4() {
    ifconfig "$1" 2>/dev/null | awk '/inet addr:/ {gsub("addr:", "", $2); print $2}' | head -n 1
}

check_ipv6() {
    ifconfig "$1" 2>/dev/null | awk '/inet6 addr:/ {print $3}' | head -n 1
}

wait_for_seconds() {
    sleep "$1"
}

# Check if there are active softcams (excludes softcam.None)
if find /etc/init.d/ -maxdepth 1 -type f -name "softcam.*" ! -name "softcam.None" | grep -q .; then
    log_message "Active Softcam detected. Starting network check..."

    for iface in eth0 eth1 wlan0 wlan3; do
        if grep -q "auto $iface" /etc/network/interfaces; then
            if grep -qi "iface $iface inet dhcp" /etc/network/interfaces || \
               grep -qi "iface $iface inet6 dhcp" /etc/network/interfaces; then

                log_message "Interface $iface configured for DHCP.."

                ATTEMPTS=0
                MAX_ATTEMPTS=20

                while [ "$ATTEMPTS" -lt "$MAX_ATTEMPTS" ]; do
                    IPV4=$(check_ipv4 "$iface")
                    IPV6=$(check_ipv6 "$iface")

                    if [ -n "$IPV4" ] || [ -n "$IPV6" ]; then
                        log_message "IP obtained for $iface - IPv4: ${IPV4:-none}, IPv6: ${IPV6:-none}"
                        break
                    fi

                    log_message "$ATTEMPTS: No IP yet for $iface, retrying..."
                    wait_for_seconds 1
                    ATTEMPTS=$((ATTEMPTS + 1))
                done

                if [ "$ATTEMPTS" -eq "$MAX_ATTEMPTS" ]; then
                    log_message "Max attempts reached. No IP for $iface. Exiting."
                    exit 1
                fi

                break
            fi
        fi
    done

    log_message "Contents of /etc/resolv.conf:"
    cat /etc/resolv.conf >> "$log_file"

    log_message "Check DNS (ping to google.com)..."
    if ping -c 1 -W 2 google.com > /dev/null 2>&1; then
        log_message "DNS working (google.com reachable)."
    else
        log_message "DNS not working (google.com unreachable)."
    fi
else
    log_message "No active softcam. Skipping DHCP wait."
fi
