#!/bin/bash
[ -e /etc/enigma2/settings ] && exit 0

ROOTFS=/
LOG=/home/root/FastRestore.log

#ROOTFS=/tmp/
#LOG=/dev/tty

restoreUserDB() {
	$(python - <<END
import sys
sys.path.append('/usr/lib/enigma2/python/Plugins/SystemPlugins/SoftwareManager')
from ShellCompatibleFunctions import restoreUserDB
restoreUserDB()
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

get_rightset() {
	RIGHTSET=$(python - <<END
import sys
sys.path.append('/usr/lib/enigma2/python/Plugins/SystemPlugins/SoftwareManager')
import ShellCompatibleFunctions
print ShellCompatibleFunctions.MANDATORY_RIGHTS
END
	)
}

get_restore_mode() {
	settings=0
	plugins=0
	fast=0
	turbo=0

	for i in hdd usb backup; do
		[ -e /media/${i}/images/config/settings ] && settings=1
		[ -e /media/${i}/images/config/plugins ] && plugins=1
		[ -e /media/${i}/images/config/fast ] && fast=1
		[ -e /media/${i}/images/config/turbo ] && turbo=1
	done

	for i in hdd usb backup; do
		[ -e /media/${i}/images/config/noplugins ] && plugins=0
	done

	fast=$((fast | turbo))
}

show_logo() {
	BOOTLOGO=/usr/share/restore.mvi
	[ ! -e $BOOTLOGO ] && BOOTLOGO=/usr/share/bootlogo.mvi
	/usr/bin/showiframe ${BOOTLOGO}
}

lock_device() {
	DEV=/dev/null
	[ -e /dev/dbox/oled0 ] && DEV=/dev/dbox/oled0

	if [ "x$DEV" != "x/dev/null" ]; then
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
		echo -n "${task} ${spin}" 1>&200
		sleep $delay
	done
}

get_restore_mode

[ $fast -eq 1 ] || exit 0
[ $settings -eq 1 ] || exit 0

show_logo
lock_device
get_backupset

[ ! -e "$backuplocation/enigma2settingsbackup.tar.gz" ] && exit 0

[ "x$LOG" != "x/dev/tty" ] && rm $LOG
touch $LOG
echo "Extracting saved settings from $backuplocation/enigma2settingsbackup.tar.gz" >> $LOG
(get_rightset ; get_blacklist ; busybox tar -C $ROOTFS -xzvf $backuplocation/enigma2settingsbackup.tar.gz ${BLACKLIST} >>$LOG 2>>$LOG ; eval ${RIGHTSET} >>$LOG 2>>$LOG ; restoreUserDB ; touch /tmp/restore_skins ) &
spinner $! "Settings "
echo >>$LOG

echo "Restarting network" >>$LOG
(/etc/init.d/networking restart >>$LOG ; /etc/init.d/autofs restart >>$LOG ; sleep 3) &
spinner $! "Network "
echo >>$LOG

if [ $plugins -eq 1 ] && [ -e ${ROOTFS}tmp/installed-list.txt ]; then
	echo "Re-installing previous plugins" >> $LOG
	pkgs=$(<${ROOTFS}tmp/installed-list.txt)
	(opkg update && opkg install $pkgs >>$LOG 2>>$LOG) &
	spinner $! "Plugins "
	echo >>$LOG
fi

for i in hdd usb backup; do
	if [ -e /media/${i}/images/config/myrestore.sh ]; then
		echo "Executing MyRestore script in $i" >> $LOG
		(. /media/${i}/images/config/myrestore.sh >>$LOG 2>>$LOG) &
		spinner $! "MyRestore "
		echo >>$LOG
	fi
done

[ $turbo -eq 0 ] && echo "Running in fast mode ... reboot ..." >>$LOG && sync && reboot

echo "Running in turbo mode ... remounting everything ..." >>$LOG
mounts=$(mount | grep -E '(^/dev|^//)' | awk '{ print $1 }')

for i in $mounts; do
	echo "Unmounting $i ..." >>$LOG
	umount $i >>$LOG 2>>$LOG
done
echo "Mounting all ..." >>$LOG
mount -a >>$LOG 2>>$LOG
mdev -s
[ -e "${ROOTFS}etc/init.d/hostname.sh" ] && ${ROOTFS}etc/init.d/hostname.sh
[ -e "${ROOTFS}etc/init.d/modload.sh" ] && ${ROOTFS}etc/init.d/modload.sh
[ -e "${ROOTFS}etc/init.d/softcam" ] && ${ROOTFS}etc/init.d/softcam restart
echo >>$LOG
echo "Done.">>$LOG
echo -n "OpenATV" >&200
flock -u 200
exit 0
