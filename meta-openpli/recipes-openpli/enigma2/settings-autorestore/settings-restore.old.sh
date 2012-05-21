#! /bin/sh
BACKUPDIR=$1

# suicide...
rm -f /etc/rcS.d/S*settingsrestore

echo "Old style restoring from: ${BACKUPDIR}"

[ -d ${BACKUPDIR}/backup/enigma2 ] && {
  mkdir -p /etc/enigma2
  cp -a ${BACKUPDIR}/backup/enigma2/* /etc/enigma2/
  touch /tmp/.set_restored
}

[ -d ${BACKUPDIR}/backup/.ssh ] && cp -r ${BACKUPDIR}/backup/.ssh /home/root
[ -f ${BACKUPDIR}/backup/dropbear_rsa_host_key ] && cp ${BACKUPDIR}/backup/dropbear_rsa_host_key /etc/dropbear
for file in CCcam.cfg CCcam.prio CCcam.channelinfo CCcam.providers radegast.cfg newsreader.conf NETcaster.conf resolv.conf
do
	[ -f ${BACKUPDIR}/backup/$file ] && cp ${BACKUPDIR}/backup/$file /etc/
done
[ -f ${BACKUPDIR}/backup/peer.cfg ] && cp ${BACKUPDIR}/backup/peer.cfg /etc/keys
[ -f ${BACKUPDIR}/backup/mg_cfg ] && cp ${BACKUPDIR}/backup/mg_cfg /etc/keys
[ -f ${BACKUPDIR}/backup/ignore.list ] && cp ${BACKUPDIR}/backup/ignore.list /etc/keys
[ -f ${BACKUPDIR}/backup/priority.list ] && cp ${BACKUPDIR}/backup/priority.list /etc/keys
[ -f ${BACKUPDIR}/backup/replace.list ] && cp ${BACKUPDIR}/backup/replace.list /etc/keys
[ -f ${BACKUPDIR}/backup/newcamd.conf ] && cp ${BACKUPDIR}/backup/newcamd.conf /etc/tuxbox/config
[ -f ${BACKUPDIR}/backup/smb.conf ] && cp ${BACKUPDIR}/backup/smb.conf /etc/samba
[ -d ${BACKUPDIR}/backup/keys ] && cp ${BACKUPDIR}/backup/keys/* /etc/keys/
if [ -f ${BACKUPDIR}/backup/fstab ]
then
  for fstype in cifs nfs swap
  do
    grep -q " ${fstype} " /etc/fstab || grep " ${fstype} " ${BACKUPDIR}/backup/fstab >>/etc/fstab
  done
fi
[ -f ${BACKUPDIR}/backup/crontab ] && {
  crontab ${BACKUPDIR}/backup/crontab
}
[ -d ${BACKUPDIR}/backup/network ] &&  cp -rp ${BACKUPDIR}/backup/network/* /etc/network/
[ -d ${BACKUPDIR}/backup/default ] &&  cp -rp ${BACKUPDIR}/backup/default/* /etc/default/
[ -d ${BACKUPDIR}/backup/tuxbox ] &&  cp -rp ${BACKUPDIR}/backup/tuxbox/* /etc/tuxbox/

mergerootpwd()
{
	grep "^root:" ${BACKUPDIR}/backup/passwd
	grep -v "^root:" /etc/passwd
}

if [ -f ${BACKUPDIR}/backup/passwd ]
then
	mergerootpwd > /tmp/passwd
	# QA check - we don't want a passwd file without root entry
	if grep -q "^root:" /tmp/passwd
	then
		cp /tmp/passwd /etc/passwd
	fi
	rm -f /tmp/passwd
fi

