#!/bin/sh


notify() {
	# we don't really depend on the hotplug_e2_helper, but when it exists, call it
	if [ -x /usr/bin/hotplug_e2_helper ] ; then
		/usr/bin/hotplug_e2_helper $ACTION /dev/$MDEV /block/$DEVBASE/device
	fi
}

case "$ACTION" in
	add|"")
		ACTION="add"
		FSTYPE=`blkid /dev/${MDEV} | grep -v 'TYPE="swap"' | grep ${MDEV} | sed -e "s/.*TYPE=//" -e 's/"//g'`
		FLASHEXPANDERDEV=`cat /proc/mounts | grep '.FlashExpander' | cut -d " " -f1`
		MOUNTPOINT=`cat /proc/mounts | grep ${FLASHEXPANDERDEV} | cut -d " " -f2`
		if [ -z "$FSTYPE" ] ; then
			exit 0
		fi
		# check if already mounted
		if grep -q "^/dev/${MDEV} " /proc/mounts ; then
			if [ ! "${FLASHEXPANDERDEV}" == "/dev/${MDEV}" ] || [[ "$MOUNTPOINT"  =~ .*"/media/"* ]]; then 
				# Already mounted
				exit 0
			fi
		fi
		DEVBASE=`expr substr $MDEV 1 3`
		# check for "please don't mount it" file
		if [ -f "/dev/nomount.${DEVBASE}" ] ; then
			# blocked
			exit 0
		fi
		# check for full-disk partition
		if [ "${DEVBASE}" == "${MDEV}" ] ; then
			if [ -d /sys/block/${DEVBASE}/${DEVBASE}1 ] ; then
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
		if [ "${DEVBASE}" == "mmc" ] ; then
			DEVBASE="mmcblk0"
		fi
		boxtype=`cat /etc/model`
		if [ "$boxtype&&" == "hd2400&&" ] && [ "${DEVBASE}" == "sdb" ] && [ -d /media/hdd ]; then
				# workaround intern/extern detection
				umount /media/hdd
				rmdir -rf /media/hdd
				touch /tmp/sda
		fi
		# first allow fstab to determine the mountpoint
		if ! mount /dev/$MDEV > /dev/null 2>&1 ; then
			# no fstab entry, use automatic mountpoint
			REMOVABLE=`cat /sys/block/$DEVBASE/removable`
			readlink -fn /sys/block/$DEVBASE/device | grep -qs 'pci\|ahci'
			EXTERNAL=$?
			if [ "${REMOVABLE}" -eq "0" -a $EXTERNAL -eq 0 ] ; then
				# mount the first non-removable internal device on /media/usb 
				DEVICETYPE="usb"
echo "first internal" >> /etc/mdev/log.txt
			else
				MODEL=`cat /sys/block/$DEVBASE/device/model`
				MODEL1=`cat /sys/block/$DEVBASE/device/type`
				if [ "$MODEL" == "USB CF Reader   " ]; then
					DEVICETYPE="cf"
				elif [ "$MODEL" == "Compact Flash   " ]; then
					DEVICETYPE="cf"
				elif [ "$MODEL" == "USB SD Reader   " ]; then
					DEVICETYPE="mmc1"
				elif [ "$MODEL" == "USB SD  Reader  " ]; then
					DEVICETYPE="mmc1"
				elif [ "$MODEL" == "SD/MMC          " ]; then
					DEVICETYPE="mmc1"
				elif [ "$MODEL" == "USB MS Reader   " ]; then
					DEVICETYPE="mmc1"
				elif [ "$MODEL" == "SM/xD-Picture   " ]; then
					DEVICETYPE="mmc1"
				elif [ "$MODEL" == "USB SM Reader   " ]; then
					DEVICETYPE="mmc1"
				elif [ "$MODEL" == "MS/MS-Pro       " ]; then
					DEVICETYPE="mmc1"
				elif [ "$MODEL1" == "SD" ]; then
					DEVICETYPE="mmc1"
				else

					# enumerate usb drives
					if grep -q "/media/usb" /proc/mounts ; then
						
						range=`grep -c /media/usb /proc/mounts`
						echo "Parameter 1: "$range >> /etc/mdev/log.txt
						if [ $range -ge 1 ]; then

						      for i in $(seq 1 $range) ; do
							    if ! grep -q "/media/usb$i" /proc/mounts ; then
							    echo "Parameter 1: "$i >> /etc/mdev/log.txt
								  DEVICETYPE="usb$i"
								  break
							    fi
						      done
						else 
						      DEVICETYPE="usb"
						fi

					else
						# mount the first removable device on /media/usb only then no other internal hdd present
						DEVICETYPE="usb"
						DEVLIST=`ls -1 /sys/block | grep "sd[a-z]"`
						for DEV in $DEVLIST; do
							DEVBASE=`expr substr $DEV 1 3`
							readlink -fn /sys/block/$DEVBASE/device | grep -qs 'pci\|ahci'
							EXTERNAL=$?
							if [ "${REMOVABLE}" -eq "0" -a $EXTERNAL -eq 0 ] ; then
								DEVICETYPE="usb"
								break
							fi
						done
					fi
				fi
			fi
			# Use mkdir as 'atomic' action, failure means someone beat us to the punch
			MOUNTPOINT="/media/$DEVICETYPE"

			# Remove mountpoint not being used
			if [ -z "`grep $MOUNTPOINT /proc/mounts`" ] ; then
				rm -rf $MOUNTPOINT
			fi
			if ! mkdir $MOUNTPOINT ; then
				MOUNTPOINT="/media/$MDEV"
				mkdir -p $MOUNTPOINT
			fi
			if ! mount -t auto /dev/$MDEV "${MOUNTPOINT}" ; then
				rmdir "${MOUNTPOINT}"
			fi
			boxtype=`cat /etc/model`
			if [ "$boxtype&&" == "hd2400&&" ] && [ "${MDEV}" == "sdb1" ] && [ -f "/tmp/sda" ]; then
				mkdir -p /media/usb
				if ! mount -t auto /dev/sda "/media/usb" ; then
					rmdir "/media/usb"
				fi
				rm /tmp/sda
			fi
		fi
		;;
	remove)
		MOUNTPOINT=`grep "^/dev/$MDEV\s" /proc/mounts | cut -d' ' -f 2`
		if [ -z "$MOUNTPOINT" ] ; then
			MOUNTPOINT="/media/$MDEV"
		fi
		umount $MOUNTPOINT || umount /dev/$MDEV
		rmdir $MOUNTPOINT
		;;
	*)
		# Unexpected keyword
		exit 1
		;;
esac

notify
