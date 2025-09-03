#!/bin/sh
#
# Called from udev
#
# Attempt to mount any added block devices and umount any removed devices

MOUNT="/bin/mount"
PMOUNT="/usr/bin/pmount"
UMOUNT="/bin/umount"

# File for known devices
KNOWN_DEVICES_FILE="/etc/udev/known_devices"

log() {
	# comment to enable logging
	if [ ! -f /etc/udev/udev.debug ]; then
		return
	fi

	if [ $# -eq 1 ]; then
		echo "udev/mount.sh" "$1" >> $LOG
		#logger "udev/mount.sh" "$1"
	else
		echo "udev/mount.sh" "$DEVNAME: $1 $2" >> $LOG
		#logger "udev/mount.sh" "$DEVNAME: $1 $2"
	fi
}

for line in `grep -h -v ^# /etc/udev/mount.ignorelist /etc/udev/mount.ignorelist.d/*`
do
	if [ ` expr match "$DEVNAME" "$line" ` -gt 0 ];
	then
		logger "udev/mount.sh" "[$DEVNAME] is blacklisted, ignoring"
		exit 0
	fi
done

if [[ $ID_PART_ENTRY_NAME =~ ^(kernel[0-9]*|linuxkernel[0-9]*|rootfs[0-9]*|startup|userdata|dreambox-rootfs)$ ]] ; then
	log "PARTLABEL excludes $ID_PART_ENTRY_NAME"
	exit 0
fi

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

	if [ "${DEVBASE:0:6}" == "mmcblk" ] ; then
		PARTNUM=`expr substr $NAME 9 1`
	else
		PARTNUM=`expr substr $NAME 4 1`
	fi
	
	log ">" "PARTNUM = $PARTNUM"

	# Get the device model
	if [ -f /sys/block/$DEVBASE/device/model ]; then
		MODEL=`cat /sys/block/$DEVBASE/device/model`
	else
		MODEL="unknown device"
	fi
	if [ -f /sys/block/$DEVBASE/device/type ]; then
		MODEL1=`cat /sys/block/$DEVBASE/device/type`
	else
		MODEL1="unknown device"
	fi
	log ">" "MODEL = $MODEL"
	log ">" "TYPE = $MODEL1"

	# external?
	readlink -fn /sys/block/$DEVBASE/device | grep -qs 'pci\|ahci\|sata'
	EXTERNAL=$?
	REMOVABLE=`cat /sys/block/$DEVBASE/removable`
	
	log ">" "EXTERNAL = $EXTERNAL"
	log ">" "REMOVABLE = $REMOVABLE"

	# make sure this next bit doesn't run concurrently
	lock

	# Figure out a mount point to use
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

		if [ "${REMOVABLE}" -eq "0" -a $EXTERNAL -eq 0 ]; then
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
				elif [ "$MODEL1" == "SD	            " ]; then
					LABEL="mmc"
				elif [ "$MODEL1" == "SD              " ]; then
					LABEL="mmc"
				elif [ "$MODEL1" == "SD" ]; then
					LABEL="mmc"
				else
					#echo "[mdev-mount.sh] USB device found" >> $LOG
					if [ $PARTNUM -eq "1" -o $PARTNUM -eq "5" ] ; then
						#echo "[mdev-mount.sh] 1st partition found" >> $LOG
						if grep -q "/media/hdd" /proc/mounts ; then
							#echo "[mdev-mount.sh] /media/hdd exists" >> $LOG
							if grep -q "/media/usb" /proc/mounts ; then
								#echo "[mdev-mount.sh] /media/usb exists" >> $LOG
								LABEL=$NAME
							else
								LABEL="usb"
							fi
						else
							# mount the first removable device on /media/hdd only when no other internal hdd is present
							LABEL="hdd"
							DEVLIST=`ls -1 /sys/block | grep "sd[a-z]\|mmcblk[0-9]"`
							for DEV in $DEVLIST; do
								DEVBASE=`expr substr $DEV 1 3`
								if [ "${REMOVABLE}" -eq "0" -a $EXTERNAL -eq 0 ] ; then
									LABEL="usb"
									#echo "[mdev-mount.sh] internal sdx detected -> mount as USB" >> $LOG
									break
								fi
							done
						fi
					else
						#echo "[mdev-mount.sh] next partition $PARTNUM of USB device found" >> $LOG
						# Mount next partition as detected device
						LABEL=$NAME
					fi
				fi
			fi
		fi
	fi

	log ">" "LABEL = $LABEL"

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

if [ "$ACTION" = "add" ]; then
	FLASHEXPANDERDEV=`cat /proc/mounts | grep '.FlashExpander' | cut -d " " -f1`
	if [ -n "$FLASHEXPANDERDEV" ]; then
		MOUNTPOINT=`cat /proc/mounts | grep ${FLASHEXPANDERDEV} | cut -d " " -f2`
	else
		MOUNTPOINT=""
	fi

	if [ ${name:0:2} == "sr" ]; then
		log "CD/DVD Detectet. $DEVNAME"
		exit 0
	fi

	if [ -z "$ID_FS_TYPE" ]; then
		log "Filesystem not exist. $DEVNAME"
	#	exit 0
	fi

	# check if already mounted
	if grep -q "^${DEVNAME} " /proc/mounts ; then
		if [ ! "${FLASHEXPANDERDEV}" == "${DEVNAME}" ] || [[ "$MOUNTPOINT"  =~ .*"/media/"* ]]; then 
			log  "Already mounted: ${DEVNAME}"
			exit 0
		fi
	fi
	# Check if the device is already in /etc/fstab
	if grep -qs "$DEVNAME" /etc/fstab && ! ps aux | grep -v grep | grep -q enigma2; then
		log "Device $DEVNAME is already in /etc/fstab, skipping mount."
		exit 0
	fi

	# Check if the device is already in /etc/fstab and UUID not empty
	if [ -z "$ID_FS_UUID" ]; then
		log "UUID is empty, skipping /etc/fstab check."
	else
		if grep -qs "UUID=$ID_FS_UUID" /etc/fstab && ! ps aux | grep -v grep | grep -q enigma2; then
			log "UUID $ID_FS_UUID is already in /etc/fstab, skipping mount."
			exit 0
		fi
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

	# Check if a filesystem is present
	if [ -n "$ID_FS_TYPE" ]; then
		# If the device isn't mounted at this point, it isn't
		# configured in fstab (note the root filesystem can show up as
		# /dev/root in /proc/mounts, so check the device number too)
		if ! ps aux | grep -v grep | grep -q enigma2; then
			if expr $MAJOR "*" 256 + $MINOR != `stat -c %d /`; then
				grep -q "^$DEVNAME " /proc/mounts || automount
			fi
		fi
	else
		log "No filesystem detected for device $DEVNAME, skipping."
	fi

	# inform E2 of the hotplug action only for partitions
	# Check if enigma2 process is running
	if ps aux | grep -v grep | grep -q enigma2; then
		log "enigma2 running"
		notify true
	else
		notify false
	fi
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

