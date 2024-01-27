files=$(find /etc/init.d/ -maxdepth 1 -type f -name "softcam.*" ! -name "softcam.None")
if [ -n "$files" ]; then
log_file="/home/root/logs/network.log"
echo "" > "$log_file"

wait_for_seconds() {
    sleep $1
}

check_ipv4() {
    ip -4 -o addr show $1 | awk '{print $4}' | cut -d "/" -f 1
}

check_ipv6() {
    ip -6 -o addr show $1 scope global | awk '{print $4}' | cut -d "/" -f 1
}

log_message() {
    echo "$(date "+%Y-%m-%d %H:%M:%S"): $1" >> "$log_file"
}

for iface in eth0 eth1 wlan0 wlan3; do
    if grep -q "auto $iface" /etc/network/interfaces; then
        dhcp_ipv4_line=$(grep -i "iface $iface inet dhcp" /etc/network/interfaces)
        dhcp_ipv6_line=$(grep -i "iface $iface inet6 dhcp" /etc/network/interfaces)

        if [ -n "$dhcp_ipv4_line" ] || [ -n "$dhcp_ipv6_line" ]; then
            log_message "Found $iface with DHCP configuration."

            attempts=0
            max_attempts=10
            while [ $attempts -lt $max_attempts ]; do
                ip_address_ipv4=$(check_ipv4 $iface)
                ip_address_ipv6=$(check_ipv6 $iface)

                if [ -n "$ip_address_ipv4" ] || [ -n "$ip_address_ipv6" ]; then
                    log_message "IP addresses obtained for $iface: IPv4: $ip_address_ipv4, IPv6: $ip_address_ipv6"
                    break
                else
                    log_message "$attempts:No valid IP addresses obtained for $iface after waiting. Retrying..."
                    wait_for_seconds 0.5
                    ((attempts++))
                fi
            done

            if [ $attempts -eq $max_attempts ]; then
                log_message "Maximum attempts reached. No valid IP addresses obtained for $iface. Exiting..."
                exit 1
            fi

            break
        fi
    fi
done

log_message "Resolv.conf content:"
cat /etc/resolv.conf >> "$log_file"

log_message "Checking DNS..."
ping -c 1 google.com >> "$log_file"
fi
