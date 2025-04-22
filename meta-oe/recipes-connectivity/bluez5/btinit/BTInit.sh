#!/bin/sh

### BEGIN INIT INFO
# Provides:          bluetooth_manager
# Required-Start:    $remote_fs $syslog
# Required-Stop:     $remote_fs $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Init script for Bluetooth Manager
### END INIT INFO

PATH=/sbin:/bin:/usr/sbin:/usr/bin
SETTINGS_FILE=/etc/enigma2/settings
INFO_FILE=/usr/lib/enigma.info
BTAUDIO_FILE=/proc/stb/audio/btaudio
COMMANDCONNECT="/usr/lib/enigma2/python/Plugins/Extensions/BTDevicesManager/BTAudioConnect"
LOG_TAG="BluetoothManager"

rm -rf /run/dbus/pid

log() {
    logger -t "$LOG_TAG" "$1"
}

if [ -f "$SETTINGS_FILE" ]; then
    AUDIO_ADDRESS=$(grep -m 1 '^config.btdevicesmanager.audioaddress=' "$SETTINGS_FILE" | cut -d'=' -f2 | tr -d "'\"")
    AUDIO_CONNECT=$(grep -m 1 '^config.btdevicesmanager.audioconnect=' "$SETTINGS_FILE" | cut -d'=' -f2 | tr -d "'\"")
fi

# If AUDIO_ADDRESS is empty, set AUDIO_CONNECT to False
if [ -z "$AUDIO_ADDRESS" ]; then
    AUDIO_CONNECT="False"
fi

# If AUDIO_CONNECT is not set or is not True, default to False
if [ -z "$AUDIO_CONNECT" ] || [ "$AUDIO_CONNECT" != "True" ]; then
    AUDIO_CONNECT="False"
fi

if [ -f "$INFO_FILE" ]; then
    MODEL=$(grep -m 1 '^model=' "$INFO_FILE" | cut -d'=' -f2 | tr -d "'\"")
fi

if [ "$MODEL" = "inihdp" ]; then
    log "inihdp: Loading driver"
    modprobe rtk_btusb &
fi

start() {
    log "Starting..."

    #if [ "$MODEL" = "gbmv200" ]; then
    #    log "Attaching HCI for sprd"
    #    hciattach_sprd /dev/ttyBT0 sprd > /var/log/hciattach.log &
    #fi

    hciconfig hci0 up > /dev/null 2>&1 && log "Attaching hci0"
    (hcitool dev | awk 'NR==2 {print $2}' | while read -r BT_MAC; do
        log "Bluetooth MAC: $BT_MAC"
    done) &

    if [ -f "$BTAUDIO_FILE" ] && [ -n "$AUDIO_ADDRESS" ]; then
        if [ "$AUDIO_CONNECT" = "True" ]; then
            log "Connecting to audio device: $AUDIO_ADDRESS"
            "$COMMANDCONNECT" "$AUDIO_ADDRESS" &
        else
            log "Connecting to audio device default"
            "$COMMANDCONNECT" &
        fi
    fi
}

stop() {
    if [ "$MODEL" = "inihdp" ]; then
        log "inihdp: Stopping driver"
        rmmod rtk_btusb &
    fi
}

case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        sleep 1
        start
        ;;
    *)
        echo "Usage: $0 {start|stop|restart}"
        exit 1
        ;;
esac

exit 0
