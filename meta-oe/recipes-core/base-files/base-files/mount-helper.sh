#!/bin/sh

#LOG='/etc/udev/mdev-mount.log'

# (e)udev compatibility
[[ -z $MDEV ]] && MDEV=$(basename $DEVNAME)

BLACKLISTED="@BLACKLISTED@"
FIRST_MEDIA="hdd"

## device information log
#echo  >> $LOG
#echo  >> $LOG
#echo "**************************" >> $LOG
#echo  >> $LOG
#echo "Action= "$ACTION >> $LOG
#echo "Hotplug count ="$SEQNUM >> $LOG
#echo "Major= "$MAJOR >> $LOG
#echo "Mdev= "$MDEV >> $LOG
#echo "Devpath= "$DEVPATH >> $LOG
#echo "Devtype= "$DEVTYPE >> $LOG
#echo "Subsystem= "$SUBSYSTEM >> $LOG
#echo "Minor= "$MINOR >> $LOG
#echo "Physdevpath= "$PHYSDEVPATH >> $LOG
#echo "Physdevdriver= "$PHYSDEVDRIVER >> $LOG
#echo "Physdevbus= "$PHYSDEVBUS >> $LOG
#echo "Working directory= "$PWD >> $LOG
#echo  >> $LOG


notify() {
	# we don't really depend on the hotplug_e2_helper, but when it exists, call it
	if [ -x /usr/bin/hotplug_e2_helper ] ; then
		/usr/bin/hotplug_e2_helper $ACTION /dev/$MDEV /block/$DEVBASE/device
	fi
}

case $ACTION in
	add|"")
		ACTION="add"
		FSTYPE=`blkid /dev/${MDEV} | grep -v 'TYPE="swap"' | grep ${MDEV} | sed -e "s/.*TYPE=//" -e 's/"//g'`
		if [ -z "$FSTYPE" ] ; then
			exit 0
		fi
		# check if already mounted
		if grep -q "^/dev/${MDEV} " /proc/mounts ; then
			# Already mounted
			exit 0
		fi
		DEVCHECK=`expr substr $MDEV 1 7`
		DEVCHECK2=`expr substr $MDEV 1 3`
		# blacklisted devices
		for black in $BLACKLISTED; do
			if [ "$DEVCHECK" == "$black" ] || [ "$DEVCHECK2" == "$black" ] ; then
				exit 0
			fi
		done
		DEVCHECK=`expr substr $MDEV 1 6`
		if [ "${DEVCHECK}" == "mmcblk" ] ; then
			DEVBASE=`expr substr $MDEV 1 7`
			PARTNUM=`expr substr $MDEV 9 1`
		else
			DEVBASE=`expr substr $MDEV 1 3`
			PARTNUM=`expr substr $MDEV 4 1`
		fi
		# check for "please don't mount it" file
		if [ -f "/dev/nomount.${DEVBASE}" ] ; then
			# blocked
			exit 0
		fi
		# check for full-disk partition
		if [ "${DEVBASE}" == "${MDEV}" ] ; then
			if [ -d /sys/block/${DEVBASE}/${DEVBASE}1 -o -d /sys/block/${DEVBASE}/${DEVBASE}p1 ] ; then
				# Partition detected, just tell and quit
				notify
				exit 0
			fi
			if [ ! -f /sys/block/${DEVBASE}/size ] ; then
				# No size at all
				exit 0
			fi
			if [ `cat /sys/block/${DEVBASE}/size` == 0 ] ; then
				# empty device, bail out
				exit 0
			fi
		fi
		# due to multiple calls of mdev mount only first partition as usual, others as devicename.
		# first allow fstab to determine the mountpoint
		if ! mount /dev/$MDEV > /dev/null 2>&1 ; then
			#echo "[mdev-mount.sh] no fstab entry, use automatic mountpoint" >> $LOG
			# no fstab entry, use automatic mountpoint
			REMOVABLE=`cat /sys/block/$DEVBASE/removable`
			readlink -fn /sys/block/$DEVBASE/device | grep -qs 'pci\|ahci'
			EXTERNAL=$?
			if [ "${REMOVABLE}" -eq "0" -a $EXTERNAL -eq 0 ] ; then
				#echo "[mdev-mount.sh] non removable, non external" >> $LOG
				if [ $PARTNUM -le "1" ] ; then
					#echo "[mdev-mount.sh] 1st partition found" >> $LOG
					# mount the first non-removable internal device on /media/hdd
					DEVICETYPE="hdd"
					# if mount /media/hdd already exits but an internal hdd is now found  
					# then remount the first device to the device name or to /media/usb
					# (unless the mounted device is already an internal non-removable device)
					if grep -q "/media/hdd" /proc/mounts ; then
						#echo "[mdev-mount.sh] /media/hdd exists" >> $LOG
						TEMPDEV=`cat /proc/mounts | grep /media/hdd | cut -d' ' -f 1`
						# check if mounted device is already an internal device
						DEVBASE_MOUNTED=`expr substr $TEMPDEV 6 3`
						REMOVABLE_MOUNTED=`cat /sys/block/$DEVBASE_MOUNTED/removable`
						readlink -fn /sys/block/$DEVBASE_MOUNTED/device | grep -qs 'pci\|ahci'
						EXTERNAL_MOUNTED=$?
						if [ ${REMOVABLE_MOUNTED} -eq 0 -a ${EXTERNAL_MOUNTED} -eq 0 ] ; then
							#echo "[mdev-mount.sh] $TEMPDEV non removable, non external" >> $LOG
							# mounted device is an internal device --> mount new internal device
							DEVICETYPE=$MDEV   
						else
							#echo "[mdev-mount.sh] $TEMPDEV removable or external" >> $LOG
							# switch mounts
							TEMPDEV1=`echo ${TEMPDEV} | cut -d'/' -f 3`
							umount /media/hdd || umount ${TEMPDEV}
							#echo "[mdev-mount.sh] umounted /media/hdd (preparing swap with new device found)" >> $LOG
							# Use mkdir as 'atomic' action, failure means someone beat us to the punch
							if grep -q "/media/usb" /proc/mounts ; then
								#echo "[mdev-mount.sh] /media/usb exists 1" >> $LOG
								# usb mountpoint is used --> use divicefile as usual
								MOUNTPOINT="/media/$MDEV"
							else
								#echo "[mdev-mount.sh] /media/usb doesnt exist 1" >> $LOG
								MOUNTPOINT="/media/usb"
							fi
							# Remove mountpoint not being used
							if [ -z "`grep $MOUNTPOINT /proc/mounts`" ] ; then
								find $MOUNTPOINT -type d -delete
								[ -d $MOUNTPOINT ] && rmdir $MOUNTPOINT
							fi
							if ! mkdir $MOUNTPOINT ; then
								MOUNTPOINT="/media/$TEMPDEV1"
								mkdir -p $MOUNTPOINT
							fi
							if ! mount -t auto ${TEMPDEV} "${MOUNTPOINT}" ; then
								if ! mount.exfat ${TEMPDEV} "${MOUNTPOINT}" ; then
									#echo "[mdev-mount.sh] mount failed 1" >> $LOG
									find "${MOUNTPOINT}" -type d -delete
									[ -d $MOUNTPOINT ] && rmdir "${MOUNTPOINT}"
								fi
							fi
							#echo "[mdev-mount.sh] mounted $MDEV on $MOUNTPOINT (swap complete)" >> $LOG
						fi
					fi
				else
					#echo "[mdev-mount.sh] next partition $PARTNUM of non USB device found" >> $LOG
					# Mount next partition as detected device
					DEVICETYPE=$MDEV
				fi
			else
				#echo "[mdev-mount.sh] removable or external" >> $LOG
				MODEL=`cat /sys/block/$DEVBASE/device/model`
				MODEL1=`cat /sys/block/$DEVBASE/device/type`
				if [ "$MODEL" == "USB CF Reader     " ]; then
					DEVICETYPE="cf"
				elif [ "$MODEL" == "Compact Flash   " ]; then
					DEVICETYPE="cf"
				elif [ "$MODEL" == "USB SD Reader   " ]; then
					DEVICETYPE="mmc"
				elif [ "$MODEL" == "USB SD  Reader  " ]; then
					DEVICETYPE="mmc"
				elif [ "$MODEL" == "SD/MMC          " ]; then
					DEVICETYPE="mmc"
				elif [ "$MODEL" == "USB MS Reader   " ]; then
					DEVICETYPE="mmc"
				elif [ "$MODEL" == "SM/xD-Picture   " ]; then
					DEVICETYPE="mmc"
				elif [ "$MODEL" == "USB SM Reader   " ]; then
					DEVICETYPE="mmc"
				elif [ "$MODEL" == "MS/MS-Pro       " ]; then
					DEVICETYPE="mmc"
				elif [ "$MODEL1" == "SD	            " ]; then
					DEVICETYPE="mmc"
				elif [ "$MODEL1" == "SD              " ]; then
					DEVICETYPE="mmc"
				elif [ "$MODEL1" == "SD" ]; then
					DEVICETYPE="mmc"
				else
					#echo "[mdev-mount.sh] USB device found" >> $LOG
					if [ $PARTNUM -eq "1" -o $PARTNUM -eq "5" ] ; then
						#echo "[mdev-mount.sh] 1st partition found" >> $LOG
						if grep -q "/media/hdd" /proc/mounts ; then
							#echo "[mdev-mount.sh] /media/hdd exists" >> $LOG
							if grep -q "/media/usb" /proc/mounts ; then
								#echo "[mdev-mount.sh] /media/usb exists" >> $LOG
								DEVICETYPE=$MDEV
							else
								DEVICETYPE="usb"
							fi
						else
							# mount the first removable device on /media/hdd only when no other internal hdd is present
							DEVICETYPE="hdd"
							DEVLIST=`ls -1 /sys/block | grep "sd[a-z]\|mmcblk[0-9]"`
							for DEV in $DEVLIST; do
								DEVBASE=`expr substr $DEV 1 3`
								readlink -fn /sys/block/$DEVBASE/device | grep -qs 'pci\|ahci'
								EXTERNAL=$?
								if [ "${REMOVABLE}" -eq "0" -a $EXTERNAL -eq 0 ] ; then
									DEVICETYPE="usb"
									#echo "[mdev-mount.sh] internal sdx detected -> mount as USB" >> $LOG
									break
								fi
							done
						fi
					else
						#echo "[mdev-mount.sh] next partition $PARTNUM of USB device found" >> $LOG
						# Mount next partition as detected device
						DEVICETYPE=$MDEV
					fi
				fi
			fi
			# Use mkdir as 'atomic' action, failure means someone beat us to the punch
			MOUNTPOINT="/media/$DEVICETYPE"

			# Remove mountpoint not being used
			if [ -z "`grep $MOUNTPOINT /proc/mounts`" ] ; then
				#echo "[mdev-mount.sh] rmdir $MOUNTPOINT" >> $LOG
				find $MOUNTPOINT  -type d -delete
				[ -d $MOUNTPOINT ] && rmdir $MOUNTPOINT
			fi
			if ! mkdir $MOUNTPOINT ; then
				#echo "[mdev-mount.sh] mkdir $MOUNTPOINT failed, using /media/$MDEV" >> $LOG
				MOUNTPOINT="/media/$MDEV"
				mkdir -p $MOUNTPOINT
			fi
			if ! mount -t auto /dev/$MDEV "${MOUNTPOINT}" ; then
				if ! mount.exfat /dev/$MDEV "${MOUNTPOINT}" ; then
					#echo "[mdev-mount.sh] mount failed 2" >> $LOG
					find "${MOUNTPOINT}" -type d -delete
					[ -d $MOUNTPOINT ] && rmdir "${MOUNTPOINT}"
				fi
			fi
			#echo "[mdev-mount.sh] mounted $MDEV on $MOUNTPOINT" >> $LOG
		fi
		;;
	remove)
		MOUNTPOINT=`grep "^/dev/$MDEV\s" /proc/mounts | cut -d' ' -f 2`
		if [ -z "$MOUNTPOINT" ] ; then
			MOUNTPOINT="/media/$MDEV"
		fi
		umount $MOUNTPOINT || umount /dev/$MDEV
		find $MOUNTPOINT  -type d -delete
		[ -d $MOUNTPOINT ] && rmdir $MOUNTPOINT
		#echo "[mdev-mount.sh] umounted $MOUNTPOINT" >> $LOG
		;;
	*)
		# Unexpected keyword
		exit 1
		;;
esac

notify

