#! /bin/sh
# This script is run once when your box boots for the first time. You can run
# it again later, but that may destroy settings that you did.
# Restore files from backup dir with the most recent timestamp

BACKUPDIR=/media/hdd
MACADDR=`cat /sys/class/net/eth0/address | cut -b 1,2,4,5,7,8,10,11,13,14,16,17`

# check if we have samba installed
( test -f /etc/init.d/samba.sh ) && HAS_SAMBA=yes

# check if we have nfs installed
( test -f /etc/init.d/nfsserver ) && HAS_NFS=yes

# check if we have dropbear installed
( test -f /etc/init.d/dropbear ) && HAS_DROPBEAR=yes

# Make a safety backup of the smb.conf, we may need that later
if [ -n ${HAS_SAMBA} ]; then
	SAMBACONF=/etc/samba/smb.conf
	if [ -f ${SAMBACONF} ]; then
		cp ${SAMBACONF} ${SAMBACONF}.tmp
	fi
fi

# Make a safety backup of the host key file, we may need that later
if [ -n ${HAS_DROPBEAR} ]; then
	HOSTKEY=/etc/dropbear/dropbear_rsa_host_key
	if [ -f ${HOSTKEY} ]; then
		cp ${HOSTKEY} ${HOSTKEY}.tmp
	fi
fi

if [ "$1x" == "startx" ] || [ -z "$1" ]; then
	# Best candidate:
	#	If a MAC Address dependent backup was found, use that
	#	Always use the latest version
	#	Prefer an older MAC address dependent backup to a newer one without it
	for candidate in `cut -d ' ' -f 2 /proc/mounts | grep '^/media'`; do
		candidate="${candidate//\\040/\\ }"
		if [ -d ${candidate}/backup ]; then
			if [ ! -f ${BACKUPDIR}/backup/.timestamp ]; then
				BACKUPDIR=${candidate}
			elif [ -f ${candidate}/backup/PLi-AutoBackup${MACADDR}.tar.gz ]; then
				if [ ! -f ${BACKUPDIR}/backup/PLi-AutoBackup${MACADDR}.tar.gz ]; then
					BACKUPDIR=${candidate}
				elif [ ${candidate}/backup/PLi-AutoBackup${MACADDR}.tar.gz -nt ${BACKUPDIR}/backup/PLi-AutoBackup${MACADDR}.tar.gz ]; then
					BACKUPDIR=${candidate}
				fi
			elif [ ${candidate}/backup/.timestamp -nt ${BACKUPDIR}/backup/.timestamp ]; then
				if [ ! -f ${BACKUPDIR}/backup/PLi-AutoBackup${MACADDR}.tar.gz ]; then
					BACKUPDIR=${candidate}
				fi
			fi
		fi
	done

else
	# if first arg isn't 'start', its a directory name
	BACKUPDIR=$1
fi

# check if the backup directory found or passed is valid
if [ ! -f ${BACKUPDIR}/backup/.timestamp ]; then
	echo "No valid backup location, aborting auto-restore"
	exit 0
fi

if [ -f ${BACKUPDIR}/backup/PLi-AutoBackup${MACADDR}.tar.gz ]; then
	echo "Restoring from: ${BACKUPDIR}/backup/ for ${MACADDR}"
	tar -xzf ${BACKUPDIR}/backup/PLi-AutoBackup${MACADDR}.tar.gz -C /
elif [ -f ${BACKUPDIR}/backup/PLi-AutoBackup.tar.gz ]; then
	echo "Restoring from: ${BACKUPDIR}/backup/"
	tar -xzf ${BACKUPDIR}/backup/PLi-AutoBackup.tar.gz -C /
else
	echo "PLi-AutoBackup.tar.gz not found"
	exit 1
fi

echo ${BACKUPDIR} > /tmp/backupdir

# restoring fstab entries
if [ -s /tmp/fstab ]; then
	awk '!a[$0]++' /tmp/fstab /etc/fstab >/tmp/fstab.merged
	mv /tmp/fstab.merged /etc/fstab
	grep '/media/' /tmp/fstab | while read entry
	do
		# echo splits entry on whitespace, cut to get the second entry
		path=`echo $entry | cut -d ' ' -f 2`
		if [ ! -d $path ]
		then
			echo 'Creating:' $path
			mkdir -p $path
		fi
	done
	mount -a
fi

# restore the crontab if present
[ -s /tmp/crontab ] && crontab /tmp/crontab

# restore users and passwords
if [ -f /tmp/passwd ] && [ -f /tmp/shadow ]; then
	# add any newly introduced users to the backup
	cut -d':' -f1 /etc/passwd | while read user
	do
		if ! grep "^${user}:" /tmp/passwd && ! grep "^${user}:" /tmp/shadow
		then
			grep  "^${user}:" /etc/passwd >> /tmp/passwd
			grep  "^${user}:" /etc/shadow >> /tmp/shadow
		fi
	done

	# make sure we have root entries
	if ! grep "^root:" /tmp/passwd || ! grep "^root:" /tmp/shadow; then
		grep -v  "^root:" /tmp/passwd >> /tmp/newpasswd
		grep -v  "^root:" /tmp/shadow >> /tmp/newshadow
		grep "^root:" /etc/passwd >> /tmp/newpasswd
		grep "^root:" /etc/shadow >> /tmp/newshadow
		mv /tmp/newpasswd /etc/passwd
		mv /tmp/newshadow /etc/shadow
		rm -f /tmp/passwd
		rm -f /tmp/shadow
	else
		mv /tmp/passwd /etc/passwd
		mv /tmp/shadow /etc/shadow
	fi
fi

# if we have samba installed, deal with the original smb.conf
if [ -n ${HAS_SAMBA} ]; then
	if [ -f ${SAMBACONF} ]; then
		# swap old and new config files
		mv ${SAMBACONF} ${SAMBACONF}.old
		mv ${SAMBACONF}.tmp ${SAMBACONF}

		# process the smb configuration from the backup
		python /bin/convert-smbconf.py ${SAMBACONF}.old

		# check if samba is running
		SAMBA_ON=$(pidof -s smbd)

		# stop samba
		if [ -n ${SAMBA_ON} ]; then
			/etc/init.d/samba.sh stop
		fi

		# use udev mount to remove and recreate the share
		DEVNAME=dummy ACTION=dummy source /etc/udev/scripts/mount.sh > /dev/null

		# regenerate the shares
		ACTION=add
		for FILE in /etc/samba/shares/*.conf; do
			# fetch the mount path from the share definition
			path=`grep "path\s*=\s*" $FILE | tr -d "\040\011\012\015" | cut -d'=' -f 2`
			comment=`grep "comment\s*=\s*" $FILE | tr -d "\040\011\012\015" | cut -d'=' -f 2`
			# remove the old config
			rm $FILE
			# and recreate it
			samba_share $path $comment
        done

		# start samba again
		if [ -n ${SAMBA_ON} ]; then
			/etc/init.d/samba.sh start
		fi
	fi
fi

# fix possible resolv.conf symlink recursion
find -L /etc/resolv.conf
if [ $? -eq 1 ]; then
	rm -f /etc/resolv.conf
	touch /etc/resolv.conf
fi

# if we have NFS, check for exports and restart it
if [ -n ${HAS_NFS} ]; then
	if [ -f /etc/exports -a -s /etc/exports ]; then
		/etc/init.d/nfsserver restart
	fi
fi

# if we have dropbear installed, check for  an outdated hostkey
if [ -n ${HAS_DROPBEAR} ]; then
	# get the encoding type of the current key
	TYPE=$(strings $HOSTKEY | head -n 1)
	# if an old one was restored
	if [ "${TYPE}" == "ssh-rsa" ]; then
		# restore the original
		mv ${HOSTKEY} ${HOSTKEY}.old
		mv ${HOSTKEY}.tmp ${HOSTKEY}
		# and restart dropbear
		/etc/init.d/dropbear restart
	else
		# no need to for the original
		rm -f ${HOSTKEY}.tmp
	fi
fi

# custom cron jobs in /etc?
if [[ -d /etc/cron/crontabs && ! -L /etc/cron ]]; then
	# move them to /var/spool/cron/crobtabs
	cd /etc/cron/crontabs
	for file in *; do
		mv $file /var/spool/cron/crobtabs/$file
	done
	cd /
	rm -rf /etc/cron
	/etc/init.d/busybox-cron restart
fi

# remove any temp files used
rm -f /tmp/crontab /tmp/fstab
