#!/bin/bash
[ -e /etc/enigma2/settings ] && exit 0

ROOTFS=/
LOG=/home/root/FastRestore.log

do_panic() {
	rm /media/*/images/config/noplugins 2>/dev/null || true
	rm /media/*/images/config/settings 2>/dev/null || true
	rm /media/*/images/config/plugins 2>/dev/null || true
	exit 0
}

get_restoremode() {
	settings=0
	plugins=0
	noplugins=0

	slow=0
	fast=0
	turbo=1

	for i in hdd usb backup; do
		[ -e /media/${i}/images/config/settings ] && settings=1
		[ -e /media/${i}/images/config/noplugins ] && noplugins=1
		[ -e /media/${i}/images/config/plugins ] && plugins=1
		[ -e /media/${i}/images/config/slow ] && slow=1
		[ -e /media/${i}/images/config/fast ] && fast=1 && turbo=0
	done

	# If "noplugins" and "plugins" is set at the same time, "plugins" wins
	noplugins=$((noplugins & ! plugins))


	# if neither "plugins" nor "noplugins" are set, fall back to "slow", because "ask user" can not be done in a boot script
	# "slow" takes precedence over "fast"/"turbo" if explicitely set
	fast=$((settings & (plugins | noplugins) & ! slow))
}

get_backupset() {
	backuplocation=$(python - <<END
import sys
sys.path.append('/usr/lib/enigma2/python')
from boxbranding import getBoxType, getMachineBrand, getMachineName, getImageDistro
boxtype = getBoxType()
distro = getImageDistro()
if boxtype in ('maram9', 'classm', 'axodin', 'axodinc', 'starsatlx', 'genius', 'evo', 'galaxym6') and not path.exists("/media/hdd/backup_%s_%s" % (distro, boxtype)):
	backuplocation = '/media/backup/backup_'
else:
	backuplocation = '/media/hdd/backup_'
print backuplocation+distro+"_"+boxtype
END
	)
}

get_boxtype() {
	boxtype=$(python - <<END
import sys
sys.path.append('/usr/lib/enigma2/python')
from boxbranding import getBoxType, getMachineBrand, getMachineName, getImageDistro
boxtype = getBoxType()
print boxtype
END
	)
}

show_logo() {
	BOOTLOGO=/usr/share/restore.mvi
	[ ! -e $BOOTLOGO ] && BOOTLOGO=/usr/share/bootlogo.mvi
	[ -e $BOOTLOGO ] && nohup $(/usr/bin/showiframe ${BOOTLOGO}) >/dev/null 2>&1 &
}

lock_device() {
	get_boxtype

	DEV=/dev/null
	for good in vusolo2 sf4008 sf5008; do
		if [ "$boxtype" == "$good" ]; then
			[ -e /dev/dbox/oled0 ] && DEV=/dev/dbox/oled0
			[ -e /dev/dbox/lcd0 ] && DEV=/dev/dbox/lcd0
		fi
	done

	if [ "x$DEV" != "x/dev/null" ]; then
		[ -e /proc/stb/lcd/oled_brightness ] && echo 255 > /proc/stb/lcd/oled_brightness || true
		exec 200>$DEV
		flock -n 200
	fi
}

spinner() {
	local pid=$1
	local task=$2
	local delay=0.025
	local spinstr='|/-\'
	while [ "$(ps a | awk '{print $1}' | grep $pid)" ]; do
		local temp=${spinstr#?}
		spin=$(printf "%c" "$spinstr")
		local spinstr=$temp${spinstr%"$temp"}
		if [ "x$DEV" != "x/dev/null" ]; then
			echo -n "${task} ${spin}" 1>&200
		fi
		sleep $delay
	done
}

get_rightset() {
	RIGHTSET=$(python - <<END
import sys
sys.path.append('/usr/lib/enigma2/python/Plugins/SystemPlugins/SoftwareManager')
import ShellCompatibleFunctions
print ShellCompatibleFunctions.MANDATORY_RIGHTS
END
	)
}

get_blacklist() {
	BLACKLIST=$(python - <<END
import sys
sys.path.append('/usr/lib/enigma2/python/Plugins/SystemPlugins/SoftwareManager')
import ShellCompatibleFunctions
TMPLIST=ShellCompatibleFunctions.BLACKLISTED
TMPLIST.insert(0, "")
print " --exclude=".join(TMPLIST)
END
	)
}

do_restoreUserDB() {
	$(python - <<END
import sys
sys.path.append('/usr/lib/enigma2/python/Plugins/SystemPlugins/SoftwareManager')
from ShellCompatibleFunctions import restoreUserDB
restoreUserDB()
END
	)
}

restore_settings() {
	echo >>$LOG
	echo "Extracting saved settings from $backuplocation/enigma2settingsbackup.tar.gz" >> $LOG
	echo >>$LOG
	get_rightset
	get_blacklist
	busybox tar -C $ROOTFS -xzvf $backuplocation/enigma2settingsbackup.tar.gz ${BLACKLIST} >>$LOG 2>>$LOG
	eval ${RIGHTSET} >>$LOG 2>>$LOG
	do_restoreUserDB
	touch /etc/.restore_skins
	echo >>$LOG
}

restart_network() {
	echo >>$LOG
	echo "Restarting network" >>$LOG
	echo >>$LOG
	[ -e "${ROOTFS}etc/init.d/hostname.sh" ] && ${ROOTFS}etc/init.d/hostname.sh
	[ -e "${ROOTFS}etc/init.d/networking" ] && ${ROOTFS}etc/init.d/networking restart >>$LOG
	# TODO: Find a better method to wait for network without blocking
	sleep 10
	echo >>$LOG
}

restore_plugins() {
	# Restore plugins ...
	echo >>$LOG
	echo "Re-installing previous plugins" >> $LOG
	echo >>$LOG
	echo "Updating feeds ..." >> $LOG
	opkg update >>$LOG 2>>$LOG
	echo >>$LOG
	echo "Installing plugins from feeds ..." >> $LOG
	pkgs=$(<${ROOTFS}tmp/installed-list.txt)
	opkg install $pkgs >>$LOG 2>>$LOG
	echo >>$LOG
	echo "Installing plugins from local media ..." >> $LOG
	for i in hdd usb backup; do
		if ls /media/${i}/images/ipk/*.ipk >/dev/null 2>/dev/null; then
			echo >>$LOG
			echo "${i}:" >>$LOG
			opkg install /media/${i}/images/ipk/*.ipk >>$LOG 2>>$LOG
		fi
	done
	echo >>$LOG
}

restart_services() {
	echo >>$LOG
	echo "Running in turbo mode ... remounting and restarting some services ..." >>$LOG
	echo >>$LOG

	# Linux might have initialized swap on some devices that we need to unmount ...
	[ -x /sbin/swapoff ] && swapoff -a -e 2>/dev/null
	if [ -e /etc/ld.so.conf ] ; then
		/sbin/ldconfig
	fi
	mounts=$(mount | grep -E '(^/dev/s|\b\cifs\b|\bnfs\b|\bnfs4\b)' | awk '{ print $1 }')

	for i in $mounts; do
		echo "Unmounting $i ..." >>$LOG
		umount $i >>$LOG 2>>$LOG
	done
	[ -e "${ROOTFS}etc/init.d/volatile-media.sh" ] && ${ROOTFS}etc/init.d/volatile-media.sh
	echo >>$LOG
	echo "Mounting all local filesystems ..." >>$LOG
	mount -a -t nonfs,nfs4,smbfs,cifs,ncp,ncpfs,coda,ocfs2,gfs,gfs2,ceph -O no_netdev >>$LOG 2>>$LOG
	mdev -s
	[ -x /sbin/swapon ] && swapon -a 2>/dev/null
	echo >>$LOG
	echo "Backgrounding service restarts ..." >>$LOG
	[ -e "${ROOTFS}etc/init.d/modutils.sh" ] && ${ROOTFS}etc/init.d/modutils.sh >/dev/null >&1
	[ -e "${ROOTFS}etc/init.d/modload.sh" ] && ${ROOTFS}etc/init.d/modload.sh >/dev/null >&1
	[ -e "${ROOTFS}etc/init.d/softcam" ] && nohup $(${ROOTFS}etc/init.d/softcam restart) >/dev/null >&1 &
	echo >>$LOG
}

[ -e /media/*/panic.update ] && do_panic

get_restoremode

# Only continue in fast mode (includes turbo mode)
[ $fast -eq 1 ] || exit 0

get_backupset

# Exit if there is no backup set
[ ! -e "$backuplocation/enigma2settingsbackup.tar.gz" ] && exit 0

# Show "FastRestore in progress ..." boot logo
show_logo

# Lock LCD
lock_device

# Begin logging
echo "FastRestore is restoring settings ..." > $LOG
echo >> $LOG
echo >> $LOG

# Restore settings ...
(restore_settings) &
spinner $! "Settings "
echo >>$LOG

# Restart network ...
(restart_network) &
spinner $! "Network "
echo >>$LOG

if [ $plugins -eq 1 ] && [ -e ${ROOTFS}tmp/installed-list.txt ]; then
	(restore_plugins) &
	spinner $! "Plugins "
	echo >>$LOG
fi

for i in hdd usb backup; do
	# Execute MyRestore ...
	if [ -e /media/${i}/images/config/myrestore.sh ]; then
		echo >>$LOG
		echo "Executing MyRestore script in $i" >> $LOG
		(. /media/${i}/images/config/myrestore.sh >>$LOG 2>>$LOG) &
		spinner $! "MyRestore "
		echo >>$LOG
	fi
done


# Reboot here if running in "fast" mode ...
[ $turbo -eq 0 ] && echo "Running in fast mode ... reboot ..." >>$LOG && sync && reboot


# Restart certain services and remount media in "turbo" mode ...
(restart_services) &
spinner $! "Finishing "


if [ "x$DEV" != "x/dev/null" ]; then
	# Print "OpenATV" in LCD and unlock LCD ...
	echo -n "OpenATV" >&200
	flock -u 200
fi

exit 0
