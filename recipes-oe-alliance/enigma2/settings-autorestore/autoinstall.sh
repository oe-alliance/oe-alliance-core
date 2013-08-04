#! /bin/sh
# This script is run once when your box boots for the first time.
# It installs the things from /hdd/backup/autoinstall
# or from wherever the settings were restored

BACKUPDIR=/media/hdd
INSTALLED=/etc/installed
MACADDR=`cat /sys/class/net/eth0/address | cut -b 1,2,4,5,7,8,10,11,13,14,16,17`

if [ -f /tmp/backupdir ]
then
    BACKUPDIR=`cat /tmp/backupdir`
else
	for candidate in `cat /proc/mounts | cut -d ' ' -f 2 | grep '^/media'`
	do
	   if [ -d ${candidate}/backup ]
	   then
	     if [ ! -f ${BACKUPDIR}/backup/.timestamp ]
	     then
	     	BACKUPDIR=${candidate}
	     elif [ ${candidate}/backup/.timestamp -nt ${BACKUPDIR}/backup/.timestamp ]
	     then
	     	BACKUPDIR=${candidate}
	     fi
	   fi    
	done
fi

if [ -f ${BACKUPDIR}/backup/autoinstall${MACADDR} ]
then
	AUTOINSTALL=${BACKUPDIR}/backup/autoinstall${MACADDR}
else
	AUTOINSTALL=${BACKUPDIR}/backup/autoinstall
fi

if [ -x /usr/bin/opkg ]
then
	IPKG=/usr/bin/opkg
else
	IPKG=ipkg
fi

${IPKG} list_installed | cut -d ' ' -f 1 > ${INSTALLED}
chmod 444 ${INSTALLED}

# when available, bind the console during autoinstall
[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 1 > /sys/class/vtconsole/vtcon1/bind

if [ -f ${AUTOINSTALL} ]
then
	${IPKG} update  
	for package in `cat ${AUTOINSTALL}`
	do
		packagefile=`echo ${package} | sed 's/,/ /g' | awk '{print $1}'`
		packageoption=`echo ${package} | sed 's/,/ /g' | awk '{print $2" "$3" "$4}'`
		if [ "$packageoption" == "" ] 
		then
			${IPKG} install --force-defaults ${packagefile}
		else
			${IPKG} install ${packageoption} ${packagefile}
		fi
	done
fi

# done, unbind the console
[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 0 > /sys/class/vtconsole/vtcon1/bind

# suicide...
rm -f /etc/rcS.d/S*autoinstall
