#!/bin/sh

# File di log
LOG_FILE="/home/root/logs/network.log"
mkdir -p "$(dirname "$LOG_FILE")"
echo "" > "$LOG_FILE"

# Function to write messages to the log with timestamp
log() {
    echo "$(date '+%Y-%m-%d %H:%M:%S'): $1" >> "$LOG_FILE"
}

# Function to wait for a number of seconds (also accepts decimals)
wait_for_seconds() {
    sleep "$1"
}

# Gets the IPv4 address of the interface
get_ipv4() {
    ip -4 -o addr show "$1" | awk '{print $4}' | cut -d "/" -f 1
}

# Gets the global IPv6 address of the interface
get_ipv6() {
    ip -6 -o addr show "$1" scope global | awk '{print $4}' | cut -d "/" -f 1
}

# Check if there are active softcam files (excludes softcam.None)
softcam_found() {
    find /etc/init.d/ -maxdepth 1 -type f -name "softcam.*" ! -name "softcam.None" | grep -q .
}

# Check if the interface is configured to start automatically
if softcam_found; then
    log "Active Softcam detected. Starting network check..."

    for IFACE in eth0 eth1 wlan0 wlan3; do
        # Check if the interface is configured to start automatically
        if grep -q "auto $IFACE" /etc/network/interfaces; then
            # Check if it is configured to use DHCP
            DHCP4=$(grep -i "iface $IFACE inet dhcp" /etc/network/interfaces)
            DHCP6=$(grep -i "iface $IFACE inet6 dhcp" /etc/network/interfaces)

            if [ -n "$DHCP4" ] || [ -n "$DHCP6" ]; then
                log "Interface $IFACE configured for DHCP.."

                ATTEMPTS=0
                MAX_ATTEMPTS=10

                while [ "$ATTEMPTS" -lt "$MAX_ATTEMPTS" ]; do
                    IPV4=$(get_ipv4 "$IFACE")
                    IPV6=$(get_ipv6 "$IFACE")

                    if [ -n "$IPV4" ] || [ -n "$IPV6" ]; then
                        log "IP obtained for $IFACE - IPv4: ${IPV4:-none}, IPv6: ${IPV6:-none}"
                        break
                    fi

                    log "Attempt $ATTEMPTS: No IP assigned yet for $IFACE..."
                    wait_for_seconds 0.5
                    ATTEMPTS=$((ATTEMPTS + 1))
                done

                if [ "$ATTEMPTS" -eq "$MAX_ATTEMPTS" ]; then
                    log "Error: No IP assigned to $IFACE After $MAX_ATTEMPTS attempts. Exit with error."
                    exit 1
                fi

                # IP obtained, no need to check other interfaces
                break
            fi
        fi
    done

    log "Contents of /etc/resolv.conf:"
    cat /etc/resolv.conf >> "$LOG_FILE"

    log "Check DNS (ping to google.com)..."
    if ping -c 1 -W 2 google.com > /dev/null 2>&1; then
        log "DNS working (google.com reachable)."
    else
        log "Error: DNS not working (google.com NOT reachable)."
    fi
else
    echo "No active softcam. No network control needed."
fi

