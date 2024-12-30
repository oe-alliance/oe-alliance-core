#!/bin/sh
#
# Called from udev
#
# Attempt to mount any added block devices and umount any removed devices

MOUNT="/bin/mount"
UMOUNT="/bin/umount"

# File for known devices
KNOWN_DEVICES_FILE="/etc/udev/known_devices"

for line in `grep -h -v ^# /etc/udev/mount.ignorelist /etc/udev/mount.ignorelist.d/*`
do
	if [ ` expr match "$DEVNAME" "$line" ` -gt 0 ];
	then
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
	return

	if [ $# -eq 1 ]; then
		logger "udev/mount.sh" "$1"
	else
		logger "udev/mount.sh" "$DEVNAME: $1 $2"
	fi
}

notify() {
	enigma2_running=$1
	# we don't really depend on the hotplug_e2_helper, but when it exists, call it
	if [ -x /usr/bin/hotplug_e2_helper ]; then
		if [ "$enigma2_running" = false ]; then
			/usr/bin/hotplug_e2_helper $ACTION $DEVPATH -e
		else
			/usr/bin/hotplug_e2_helper $ACTION $DEVPATH
		fi
	fi
}

samba_share() {
	# bail out if samba is not installed
	if [ ! -f /etc/init.d/samba ]; then
		log "!" "samba is not installed, no share created"
		return
	fi

	# process the parameters
	local path=$1
	local mountpoint=`basename $1`
	local model=$2
	local share

	# some mountpoint name exceptions
	if [ "$mountpoint" == "hdd" ]; then
		mountpoint="Harddisk"
	fi

	log ">" "$ACTION SAMBA share for $mountpoint"

	# process the add/remove request
	if [ "$ACTION" == "add" ]; then
		# check if we already have a share for this path
		share=`find /etc/samba -name "*.conf" -exec grep "path\s*=\s*${path}" {} \;`
		if [ -z "$share" ]; then
			# do have a share template?
			if [ -f /etc/samba/shares/share.template ]; then
				# generate a share config for this mountpoint
				echo "[$mountpoint]" > /etc/samba/shares/${mountpoint}.conf
				echo "  comment = $model" >> /etc/samba/shares/${mountpoint}.conf
				echo "  path = $path" >> /etc/samba/shares/${mountpoint}.conf
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
	else
		# unknown command, bail out
		return
	fi

	# do we have samba running?
	pidof -s smbd
	if [ $? -eq 0 ]; then
		# reload samba config
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


	# Get the device model
	if [ -f /sys/block/$DEVBASE/device/model ]; then
		MODEL=`cat /sys/block/$DEVBASE/device/model`
	else
		MODEL="unknown device"
	fi
	log ">" "MODEL = $MODEL"

	# external?
	readlink -fn /sys/block/$DEVBASE/device | grep -qs 'pci\|ahci\|sata'
	EXTERNAL=$?

	# make sure this next bit doesn't run concurrently
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

	# If no label, try to come up with one
	if [[ -z "${LABEL}" ]]; then
		# Mount mmc block devices on /media/mcc
		if [[ ${DEVBASE:0:6} == "mmcblk" ]]; then
			LABEL="mmc"
		else
			case "$MODEL" in
				"USB CF Reader   " | "Compact Flash   ")
				LABEL="cf"
				;;
				"USB SD Reader   " | "USB SD  Reader  " | "SD/MMC          " | "USB MS Reader   " | "SM/xD-Picture   " | "USB SM Reader   " | "MS/MS-Pro       ")
				LABEL="mmc"
				;;
			*)
				LABEL=""
				;;
			esac
		fi
	fi

	# label may not be a used mountpoint or local directory
	if [ ! -z "${LABEL}" ] && [ -d /media/$LABEL ]; then
		# symlink? remove it
		[[ -L /media/$LABEL ]] && rm -f /media/$LABEL
		# and something is mounted on it
		mountpoint -q /media/$LABEL && LABEL=
		# or not an empty directory
		#test -z "$(ls -A /media/$LABEL)" || LABEL=
	fi

	# If no label, use the device name
	if [[ -z "${LABEL}" ]]; then
		LABEL="$NAME"
	fi

	# Create the mountpoint for the device
	! test -d "/media/$LABEL" && mkdir -p "/media/$LABEL"

	# Silent util-linux's version of mounting auto
	if [ "x`readlink $MOUNT`" = "x/bin/mount.util-linux" ]; then
		MOUNT="$MOUNT -o silent"
	fi

	# Deal with specific file system exceptions
	case $ID_FS_TYPE in
	ntfs|exfat)
		MOUNTPOINT=/sys/fs/fuse/connections
		mount -t fusectl fusectl $MOUNTPOINT >/dev/null 2>&1
		MOUNT="$MOUNT -t fuseblk"
		;;
	ext2|ext3)
		# ext2 and ext3 devices need to be mounted with the ext4 driver
		MOUNT="$MOUNT -t ext4"
		;;
	vfat|fat|msdos)
		# If filesystem type is vfat, change the ownership group to 'disk', and
		# grant it with  w/r/x permissions.
		MOUNT="$MOUNT -t auto -o umask=007,gid=`awk -F':' '/^disk/{print $3}' /etc/group`"
		;;
	*)
		# Let mount figure it out
		MOUNT="$MOUNT -t auto"
		;;
	esac

	# Use the noatime mount option for SSD
	if [ `cat /sys/block/$DEVBASE/queue/rotational` == 0 ]; then
		MOUNT="$MOUNT -o noatime"
	fi

	# remove the concurrency lock
	unlock

	if ! $MOUNT $DEVNAME "/media/$LABEL"
	then
		log "mount.sh/automount" "$MOUNT $DEVNAME \"/media/$LABEL\" failed!"
		rm_dir "/media/$LABEL"
	else
		log "mount.sh/automount" "Auto-mount of [/media/$LABEL] successful"
		touch "/tmp/.automount-$LABEL"
		samba_share "/media/$LABEL" "$MODEL"
	fi
}

rm_dir() {
	# We do not want to rm -r populated directories
	if test "`find "$1" | wc -l | tr -d " "`" -lt 2 -a -d "$1"
	then
		! test -z "$1" && rm -r "$1"
	else
		log "mount.sh/automount" "Not removing non-empty directory [$1]"
	fi
}

# No ID_FS_TYPE for cdrom device, yet it should be mounted
name="`basename "$DEVNAME"`"
[ -e /sys/block/$name/device/media ] && media_type=`cat /sys/block/$name/device/media`

if [ "$ACTION" = "add" ] && [ -n "$DEVNAME" ] && [ -n "$ID_FS_TYPE" -o "$media_type" = "cdrom" ]; then
	FLASHEXPANDERDEV=`cat /proc/mounts | grep '.FlashExpander' | cut -d " " -f1`
	if [ -n "$FLASHEXPANDERDEV" ]; then
		MOUNTPOINT=`cat /proc/mounts | grep ${FLASHEXPANDERDEV} | cut -d " " -f2`
	else
		MOUNTPOINT=""
	fi

	if [ -z "$ID_FS_TYPE" ]; then
		log "Filesystem not exist. $DEVNAME"
		exit 0
	fi

	# check if already mounted
	if grep -q "^${DEVNAME} " /proc/mounts ; then
		if [ ! "${FLASHEXPANDERDEV}" == "${DEVNAME}" ] || [[ "$MOUNTPOINT"  =~ .*"/media/"* ]]; then 
			log  "Already mounted: ${DEVNAME}"
			exit 0
		fi
	fi
	if [[ $ID_PART_ENTRY_NAME =~ ^(kernel[0-9]*|linuxkernel[0-9]*|rootfs[0-9]*|userdata|dreambox-rootfs)$ ]] ; then
		log "PARTLABEL excludes $ID_PART_ENTRY_NAME"
		exit 0
	fi


	# blacklist boot device
	BOOTDEV=$(cat /proc/cmdline | sed -e 's/^.*root=\/dev\///' -e 's/ .*$//')
	log ">" "BOOTDEV = $BOOTDEV"
	if [ "$DEVNAME" == "$BOOTDEV" ]; then
		log "!" "exit, boot device is blacklisted"
		exit 0
	fi

	# Device name and base device
	NAME="`basename "$DEVNAME"`"
	DEVBASE=${NAME:0:7}
	if [ ! -d /sys/block/${DEVBASE} ]; then
		DEVBASE=${NAME:0:3}
	fi
	log ">" "DEVBASE = $DEVBASE"

	# blacklist partitions on the same device as the boot device
	if [[ $BOOTDEV == $DEVBASE* ]]; then
		log "!" "exit, boot device partition blacklisted"
		exit 0
	fi

	# check for "please don't mount it" file
	if [ -f "/dev/nomount.${DEVBASE}" ]; then
		# blocked
		log "!" "exit, due to a no-mount flag for $DEVBASE"
		exit 0
	fi

	# Activate swap space
	if [ "$ID_FS_TYPE" == "swap" ] ; then
		if ! grep -q "^/dev/${NAME} " /proc/swaps ; then
			swapon /dev/${NAME}
		fi
		exit 0
	fi

	# Get the device model
	if [ -f /sys/block/$DEVBASE/device/model ]; then
		MODEL=`cat /sys/block/$DEVBASE/device/model`
	else
		MODEL="unknown device"
	fi
	log ">" "MODEL = $MODEL"

	# If the device isn't mounted at this point, it isn't
	# configured in fstab (note the root filesystem can show up as
	# /dev/root in /proc/mounts, so check the device number too)
	if ! ps aux | grep -v grep | grep -q enigma2; then
		if expr $MAJOR "*" 256 + $MINOR != `stat -c %d /`; then
			grep -q "^$DEVNAME " /proc/mounts || automount
		fi
	fi

	# inform E2 of the hotplug action only for partitions
	# Check if enigma2 process is running
	if [ "$DEVTYPE" = "partition" ]; then
		if ps aux | grep -v grep | grep -q enigma2; then
			log "enigma2 running"
			notify true
		else
			notify false
		fi
	fi
fi

if [ "$ACTION" = "remove" ] || [ "$ACTION" = "change" ] && [ -x "$UMOUNT" ] && [ -n "$DEVNAME" ]; then
	for mnt in `cat /proc/mounts | grep "$DEVNAME" | cut -f 2 -d " " `
	do
		$UMOUNT $mnt
	done

	LABEL=`echo $mnt | cut -c 8-`
	log "!" "remove device $LABEL"
	samba_share "/media/$LABEL" ""
	# Remove empty directories from auto-mounter
	test -e "/tmp/.automount-$LABEL" && rm_dir "/media/$LABEL"

	# inform E2 of the hotplug action only for partitions
	# Check if enigma2 process is running
	if [ "$DEVTYPE" = "partition" ]; then
		if ps aux | grep -v grep | grep -q enigma2; then
			log "enigma2 running"
			notify true
		else
			notify false
		fi
	fi
fi

