#!/bin/sh
# Script que carga /usr en la particion extendida
# copiarlo /etc/rcS.d/S44DOMExtender.sh
# los scripts de este directorio se ejecutan siempre al arrancar 
echo "apzFX: init" >/etc/fs.log
if [ ! -e /dev/hda5 ]; then
	echo "apzFX: no hda5 found (-e)" >>/etc/fs.log
	exit 1
else
	echo "apzFX: hda5 found (-e)" >>/etc/fs.log
fi
case "$1" in
    start)
	if ! grep -qs /media/DOMExtender /proc/mounts; then
		if [ ! -d "/media/DOMExtender" ]; then
			mkdir -p /media/DOMExtender
			echo "apzFX: Creating folder" >>/etc/fs.log
		fi
		echo "apzFX: No mount. Mounting..." >>/etc/fs.log
		mount /dev/hda5 /media/DOMExtender
	else
		echo "apzFX: Already mounted"
	fi
	if grep -qs /media/DOMExtender /proc/mounts; then
		echo "apzFX: Mount ok" >>/etc/fs.log
		if [ ! -d "/media/DOMExtender/usr" ]; then
		   mkdir -p /media/DOMExtender/usr
		   echo "apzFX: Folder /media/DOMExtender/usr created." >>/etc/fs.log
		   echo "apzFX: First start. Configuring. Wait..." >>/etc/fs.log
		   echo "First start. Configuring. Wait..." >/proc/progress
		   cp -a /usr/* /media/DOMExtender/usr/
		   echo "apzFX: Configuration DONE." >>/etc/fs.log
		   echo "Configuration DONE. Restarting..." > /proc/progress
		   sleep 2
		   killall -9 enigma2
		   echo "apzFX: Restart box" >>/etc/fs.log
		fi
		if ! grep -qs '/usr' /proc/mounts; then
			if [ -d /media/DOMExtender/usr/bin ]; then
				mount -o bind /media/DOMExtender/usr /usr
				echo "apzFX: Mount bind usr" >>/etc/fs.log
			else
				echo "apzFX: No valid /usr/bin" >>/etc/fs.log
			fi
			
		else
			echo "apzFX: already mounted usr" >>/etc/fs.log
		fi	
		
	fi	
	echo "apzFX: End DOMExtender" >>/etc/fs.log
	;;
    *)
	exit 1
	;;
	
esac
