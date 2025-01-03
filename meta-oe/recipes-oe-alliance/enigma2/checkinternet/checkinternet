#!/bin/bash

# Debug flag (1 for activation, 0 for deactivation)
DEBUG=0  # Set to 0 to disable debug outputs

# Target IPs for the internet check
TARGETS=("https://www.google.com" "https://www.microsoft.com" "https://www.github.com")

# Time intervals in seconds
CHECK_INTERVAL=10

# Logging function
function log_message() {
    local message="$1"
    local level="$2"  # log level: DEBUG, INFO, etc.
    
    if [[ "$level" == "DEBUG" && $DEBUG -ne 1 ]]; then
        return  # Ignore debug outputs if DEBUG is disabled
    fi
    
    logger "[$level] $message"
}

# Function to check if the interface is up before pinging
function is_interface_up() {
    local interface="$1"
    if ip link show "$interface" | grep -q "state UP"; then
        log_message "$interface is up and ready for use." "DEBUG"
        return 0
    else
        log_message "$interface is down or not ready." "DEBUG"
        return 1
    fi
}

# Function to check internet connectivity using the specified interface
function check_internet() {
    local interface="$1"  # Active interface passed as argument

    # Check if the interface is actually up
    if ! is_interface_up "$interface"; then
        log_message "Skipping internet check as $interface is down." "DEBUG"
        return 1
    fi
    for target in "${TARGETS[@]}"; do
        log_message "Check $target via $interface ..." "DEBUG"
        if wget --timeout=1 -q --spider "$target"; then
            log_message "Internet is reachable via $target using $interface." "DEBUG"
            return 0  # Exit the function with success if any target is reachable
        else
            log_message "Failed to reach $target via $interface." "DEBUG"
        fi
    done

    return 1  # Exit the function with failure if all targets failed
}

# Initialize the previous states
declare -A previous_states

# Main loop
{
    while true; do
        log_message "Starting the check of all network interfaces..." "DEBUG"

        # Get interfaces with 'UP' status and their IPs
        interfaces_info=$(ip -brief addr show up | awk '/^(eth|wlan)/{print $1, $3}')

        # Process each active interface
        while read -r interface ip; do
            log_message "Checking $interface with IP $ip..." "DEBUG"

            if [[ -z "$interface" ]]; then
                log_message "Skipping processing: Interface name is empty." "DEBUG"
                continue
            fi

            if [[ -z "$ip" ]]; then
                log_message "$interface is UP but has no valid IP address." "DEBUG"
                if [[ "${previous_states[$interface]}" == "up" ]]; then
                    /usr/bin/hotplug_e2_helper ifdown "$interface" &
                    log_message "hotplug_e2_helper for $interface started with 'link down.'" "INFO"
                    previous_states[$interface]="down"
                fi
                continue
            fi

            previous_states[$interface]="${previous_states[$interface]:-down}"
            previous_states[${interface}_internet]="${previous_states[${interface}_internet]:-off}"

            log_message "$interface is UP with IP $ip." "DEBUG"

            # Rufe check_internet auf
            if check_internet "$interface"; then
                if [[ "${previous_states[${interface}_internet]}" != "on" ]]; then
                    /usr/bin/hotplug_e2_helper online 1 &
                    log_message "hotplug_e2_helper for $interface started with 'internet on.'" "INFO"
                    previous_states[${interface}_internet]="on"
                fi
            else
                if [[ "${previous_states[${interface}_internet]}" != "off" ]]; then
                    log_message "All internet targets failed via $interface. Marking internet as off." "INFO"
                    /usr/bin/hotplug_e2_helper online 0 &
                    log_message "hotplug_e2_helper for $interface started with 'internet off.'" "INFO"
                    previous_states[${interface}_internet]="off"
                fi
            fi
        done <<< "$interfaces_info"

        # Handle interfaces that are down but still previously registered
        for interface in "${!previous_states[@]}"; do
            if [[ "${previous_states[$interface]}" == "up" ]]; then
                if ! echo "$interfaces_info" | grep -q "^$interface "; then
                    /usr/bin/hotplug_e2_helper ifdown "$interface" & >> /tmp/hot.txt
                    log_message "hotplug_e2_helper for $interface started with 'link down.'" "INFO"
                    previous_states[$interface]="down"  # Update state
                fi
            fi
        done

        # Wait before the next check
        log_message "Waiting for $CHECK_INTERVAL seconds until the next check..." "DEBUG"
        sleep $CHECK_INTERVAL
    done
} &

# Exit the script
exit 0