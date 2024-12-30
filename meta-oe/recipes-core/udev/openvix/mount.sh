#!/bin/sh
#
# Called from udev
#
# Attempt to mount any added block devices and umount any removed devices

MOUNT="/bin/mount"
PMOUNT="/usr/bin/pmount"
UMOUNT="/bin/umount"

for line in `grep -h -v ^# /etc/udev/mount.ignorelist /etc/udev/mount.ignorelist.d/*`
do
	if [ ` expr match "$DEVNAME" "$line" ` -gt 0 ];
	then
		logger "udev/mount.sh" "[$DEVNAME] is blacklisted, ignoring"
		exit 0
	fi
done

lock() {
	LOCKFILE=/tmp/udevmount.lock

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
		echo "$1" >> /home/root/udev.log
	else
		echo "$DEVNAME: $1 $2" >> /home/root/udev.log
	fi
}

notify() {
	# we don't really depend on the hotplug_e2_helper, but when it exists, call it
	if [ -x /usr/bin/hotplug_e2_helper ]; then
		/usr/bin/hotplug_e2_helper $ACTION $DEVPATH
	fi
}

samba_share() {
	# bail out if samba is not installed
	if [ ! -f /etc/init.d/samba.sh ]; then
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
		# restart samba in the background
		log ">" "background restart of samba"
		/etc/init.d/samba.sh restart &
	fi
}

automount() {
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

	# external?
	readlink -fn /sys/block/$DEVBASE/device | grep -qs 'pci\|ahci\|sata'
	EXTERNAL=$?

	# make sure this next bit doesn't run concurrently
	lock

	# Figure out a mount point to use
	LABEL=${ID_FS_LABEL}

	# If no label, try to come up with one
	if [[ -z "${LABEL}" ]]; then

		if [ "${EXTERNAL}" -eq "0" ]; then
			# we assume it's the internal harddisk
			LABEL="hdd"
		else
			# mount mmc block devices on /media/mcc
			if [ ${DEVBASE:0:6} = "mmcblk" ]; then
				LABEL="mmc"
			else
				if [ "$MODEL" == "USB CF Reader   " ]; then
					LABEL="cf"
				elif [ "$MODEL" == "Compact Flash   " ]; then
					LABEL="cf"
				elif [ "$MODEL" == "USB SD Reader   " ]; then
					LABEL="mmc"
				elif [ "$MODEL" == "USB SD  Reader  " ]; then
					LABEL="mmc"
				elif [ "$MODEL" == "SD/MMC          " ]; then
					LABEL="mmc"
				elif [ "$MODEL" == "USB MS Reader   " ]; then
					LABEL="mmc"
				elif [ "$MODEL" == "SM/xD-Picture   " ]; then
					LABEL="mmc"
				elif [ "$MODEL" == "USB SM Reader   " ]; then
					LABEL="mmc"
				elif [ "$MODEL" == "MS/MS-Pro       " ]; then
					LABEL="mmc"
				else
					LABEL="usb"
				fi
			fi
		fi
	fi

	# label may not be a used mountpoint or local directory
	if [ ! -z "${LABEL}" ] && [ -d /media/$LABEL ]; then
		# and something is mounted on it
		mountpoint -q /media/$LABEL && LABEL=
		# or not an empty directory
		test -z "$(ls -A /media/$LABEL)" || LABEL=
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
	ext2|ext3)
		# ext2 and ext3 devices need to be mounted with the ext4 driver
		MOUNT="$MOUNT -t ext4"
		;;
	vfat|fat)
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
		logger "mount.sh/automount" "$MOUNT -t auto $DEVNAME \"/media/$LABEL\" failed!"
		rm_dir "/media/$LABEL"
	else
		logger "mount.sh/automount" "Auto-mount of [/media/$LABEL] successful"
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
		logger "mount.sh/automount" "Not removing non-empty directory [$1]"
	fi
}

# No ID_FS_TYPE for cdrom device, yet it should be mounted
name="`basename "$DEVNAME"`"
[ -e /sys/block/$name/device/media ] && media_type=`cat /sys/block/$name/device/media`

if [ "$ACTION" = "add" ] && [ -n "$DEVNAME" ] && [ -n "$ID_FS_TYPE" -o "$media_type" = "cdrom" ]; then
	if [ -x "$PMOUNT" ]; then
		$PMOUNT $DEVNAME 2> /dev/null
	elif [ -x $MOUNT ]; then
		$MOUNT $DEVNAME 2> /dev/null
	fi

	# If the device isn't mounted at this point, it isn't
	# configured in fstab (note the root filesystem can show up as
	# /dev/root in /proc/mounts, so check the device number too)
	if expr $MAJOR "*" 256 + $MINOR != `stat -c %d /`; then
		grep -q "^$DEVNAME " /proc/mounts || automount
	fi
fi

if [ "$ACTION" = "remove" ] || [ "$ACTION" = "change" ] && [ -x "$UMOUNT" ] && [ -n "$DEVNAME" ]; then
	for mnt in `cat /proc/mounts | grep "$DEVNAME" | cut -f 2 -d " " `
	do
		$UMOUNT $mnt
	done

	LABEL=`echo $mnt | cut -c 8-`
	# logger "remove device $LABEL"
	samba_share "/media/$LABEL" ""
	# Remove empty directories from auto-mounter
	test -e "/tmp/.automount-$LABEL" && rm_dir "/media/$LABEL"
fi

# inform E2 of the hotplug action
notify
