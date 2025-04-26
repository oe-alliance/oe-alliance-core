#!/bin/sh
#
# Called from udev
#
# Attempt to mount any added block devices and umount any removed devices

MOUNT="/bin/mount"
UMOUNT="/bin/umount"
LOG="/tmp/udev.log"

# File for known devices
KNOWN_DEVICES_FILE="/etc/udev/known_devices"

for line in $(grep -h -v ^# /etc/udev/mount.ignorelist /etc/udev/mount.ignorelist.d/*)
do
    if [ "$(expr match "$DEVNAME" "$line")" -gt 0 ]; then
        log "udev/mount.sh" "[$DEVNAME] is blacklisted, ignoring"
        exit 0
    fi
done

lock() {
    LOCKFILE=/var/volatile/tmp/udevmount.lock

    exec 200>$LOCKFILE

    flock -xn 200
    while [ $? -eq 1 ]; do
        sleep 1
        flock -xn 200
    done

    trap "rm -f $LOCKFILE" EXIT
}

unlock() {
    flock -u 200
    rm -f $LOCKFILE
}

log() {
    # comment to enable logging
    if [ ! -f /etc/udev/udev.debug ]; then
        return
    fi

    if [ $# -eq 1 ]; then
        echo "udev/mount.sh" "$1" >> $LOG
    else
        echo "udev/mount.sh" "$DEVNAME: $1 $2" >> $LOG
    fi
}

notify() {
    # Just notify as false for boot time
    if ps aux | grep -v grep | grep -q enigma2; then
        log "enigma2 running"
        /usr/bin/hotplug_e2_helper $ACTION $DEVPATH
    else
        /usr/bin/hotplug_e2_helper $ACTION $DEVPATH -e
    fi
}

samba_share() {
    # bail out if samba is not installed
    if [ ! -f /etc/init.d/samba ]; then
        log "!" "samba is not installed, no share created"
        return
    fi

    # Process the parameters for Samba
    local path=$1
    local mountpoint=`basename $1`
    local model=$2
    local share

    # Handle mountpoint naming exceptions
    if [ "$mountpoint" == "hdd" ]; then
        mountpoint="Harddisk"
    fi

    log ">" "$ACTION SAMBA share for $mountpoint"

    # Create samba share on add action
    if [ "$ACTION" == "add" ]; then
        # check if we already have a share for this path
        share=`find /etc/samba -name "*.conf" -exec grep "path\s*=\s*${path}" {} \;`
        if [ -z "$share" ]; then
            if [ -f /etc/samba/shares/share.template ]; then
                echo "[$mountpoint]" > /etc/samba/shares/${mountpoint}.conf
                echo "  comment = $model" >> /etc/samba/shares/${mountpoint}.conf
                echo "  path = $path" >> /etc/samba/shares/${mountpoint}.conf
                if grep -q "^config.samba.autoShareAccess=0" /etc/enigma2/settings; then
                    echo "  read only = yes" >> /etc/samba/shares/${mountpoint}.conf
                fi
                cat /etc/samba/shares/share.template >> /etc/samba/shares/${mountpoint}.conf
                log ">" "share for $path created"
            else
                log "!" "share creation failed, share template missing!"
                return
            fi
        else
            log "!" "share for $path already exists"
            return
        fi
    elif [ "$ACTION" == "remove" ]; then
        if [ -f /etc/samba/shares/${mountpoint}.conf ]; then
            rm /etc/samba/shares/${mountpoint}.conf
        fi
    fi

    # Reload samba if it's running
    pidof -s smbd
    if [ $? -eq 0 ]; then
        log ">" "background reload of samba config"
        /etc/init.d/samba reload &
    fi
}

automount() {
    # Device name and base device
    NAME="`basename "$DEVNAME"`"
    DEVBASE=${NAME:0:7}
    if [ ! -d /sys/block/${DEVBASE} ]; then
        DEVBASE=${NAME:0:3}
    fi
    log ">" "DEVBASE = $DEVBASE"

    # Device model (use device info)
    if [ -f /sys/block/$DEVBASE/device/model ]; then
        MODEL=`cat /sys/block/$DEVBASE/device/model`
    elif [ -f /sys/block/$DEVBASE/device/name ]; then
        MODEL=`cat /sys/block/$DEVBASE/device/name`
    else
        MODEL="unknown device"
    fi
    log ">" "MODEL = $MODEL"

    # External device check
    readlink -fn /sys/block/$DEVBASE/device | grep -qs 'pci\|ahci\|sata'
    EXTERNAL=$?

    # Locking to avoid concurrency
    lock

    UUID=$ID_FS_UUID
    KNOWNMOUNT=$(grep "^$UUID" "$KNOWN_DEVICES_FILE" | cut -d ':' -f2)

    if [ -z "$KNOWNMOUNT" ]; then
        log "UUID $UUID not found in known devices, using default logic"
    elif [ "$KNOWNMOUNT" = "None" ]; then
        log "UUID $UUID found with None, Skip mounting"
        exit 0
    else
        LABEL="`basename "$KNOWNMOUNT"`"
        log "UUID $UUID found with mount point: $LABEL"
    fi

    if [[ -z "${LABEL}" ]]; then
        # Try to guess the label for mmc devices
        if [[ ${DEVBASE:0:6} == "mmcblk" ]]; then
            LABEL="mmc"
        else
            LABEL="$NAME"
        fi
    fi

    if [ ! -z "${LABEL}" ] && [ -d /media/$LABEL ]; then
        mountpoint -q /media/$LABEL && LABEL=""
    fi

    if [[ -z "${LABEL}" ]]; then
        LABEL="$NAME"
    fi

    ! test -d "/media/$LABEL" && mkdir -p "/media/$LABEL"

    if [ "x`readlink $MOUNT`" = "x/bin/mount.util-linux" ]; then
        MOUNT="$MOUNT -o silent"
    fi

    # Mounting logic for different filesystems
    case $ID_FS_TYPE in
        exfat)
            MOUNT="mount.exfat-fuse"
            ;;
        ntfs)
            MOUNT="$MOUNT -t fuseblk"
            ;;
        ext2|ext3)
            MOUNT="$MOUNT -t ext4"
            ;;
        *)
            MOUNT="$MOUNT -t auto"
            ;;
    esac

    # Mount if not an SSD
    if [ `cat /sys/block/$DEVBASE/queue/rotational` == 0 ]; then
        MOUNT="$MOUNT -o noatime"
    fi

    unlock

    # Perform the mount
    if ! $MOUNT $DEVNAME "/media/$LABEL"; then
        log "mount.sh/automount" "$MOUNT $DEVNAME \"/media/$LABEL\" failed!"
        rm_dir "/media/$LABEL"
    else
        log "mount.sh/automount" "Auto-mount of [/media/$LABEL] successful"
        touch "/tmp/.automount-$LABEL"
        if ! grep -q "^config.samba.enableAutoShare=" /etc/enigma2/settings; then
            samba_share "/media/$LABEL" "$MODEL"
        fi
    fi
}

# Remove empty directories (same as before)
rm_dir() {
    if test "`find "$1" | wc -l | tr -d " "`" -lt 2 -a -d "$1"; then
        ! test -z "$1" && rm -r "$1"
    else
        log "mount.sh/automount" "Not removing non-empty directory [$1]"
    fi
}

# Main logic for add/remove actions
if [ "$ACTION" = "add" ]; then
    sleep 1  # Added delay to make sure system is ready
    automount
    notify false  # Always notify false during boot

fi

if [ "$ACTION" = "remove" ] || [ "$ACTION" = "change" ] && [ -x "$UMOUNT" ] && [ -n "$DEVNAME" ]; then
    for mnt in `cat /proc/mounts | grep "$DEVNAME" | cut -f 2 -d " " `
    do
        $UMOUNT $mnt
    done


    if [ ${name:0:2} == "sr" ]; then
        log "CD/DVD Detectet. $DEVNAME"
        exit 0
    fi

    LABEL=`echo $mnt | cut -c 8-`
    log "!" "remove device $LABEL"
    samba_share "/media/$LABEL" ""
    # Remove empty directories from auto-mounter
    test -e "/tmp/.automount-$LABEL" && rm_dir "/media/$LABEL"

    # inform E2 of the hotplug action only for partitions
    # Check if enigma2 process is running
    if ps aux | grep -v grep | grep -q enigma2; then
        log "enigma2 running"
        notify true
    else
        notify false
    fi
fi
