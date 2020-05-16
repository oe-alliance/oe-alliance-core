inherit image_types

BOOTOPTIONS_PARTITION_SIZE = "2048"

do_image_zgemmaubi[depends] = " \
	e2fsprogs-native:do_populate_sysroot \
	dosfstools-native:do_populate_sysroot \
	mtools-native:do_populate_sysroot \
"

IMAGE_CMD_zgemmaubi () {
	dd if=/dev/zero of=${WORKDIR}/bootoptions.img bs=1024 count=${BOOTOPTIONS_PARTITION_SIZE}
	mkfs.msdos -S 512 ${WORKDIR}/bootoptions.img
	echo "bootcmd=nand read 0x1FFFFC0 0x2000000 0x800000;bootm 0x1FFFFC0" >> ${WORKDIR}/STARTUP
	echo "bootcmd=nand read 0x1FFFFC0 0x2000000 0x800000;bootm 0x1FFFFC0" >> ${WORKDIR}/STARTUP_LINUX_1
	echo "bootcmd=nand read 0x1FFFFC0 0x800000 0x800000;bootm 0x1FFFFC0" > ${WORKDIR}/STARTUP_RECOVERY
	echo "imageurl https://raw.githubusercontent.com/oe-alliance/bootmenu/raw/master/${MACHINEBUILD}/images" > ${WORKDIR}/bootmenu.conf
	echo "# " >> ${WORKDIR}/bootmenu.conf
	echo "iface eth0" >> ${WORKDIR}/bootmenu.conf
	echo "dhcp yes" >> ${WORKDIR}/bootmenu.conf
	echo "# " >> ${WORKDIR}/bootmenu.conf
	echo "# for static config leave out 'dhcp yes' and add the following settings:" >> ${WORKDIR}/bootmenu.conf
	echo "# " >> ${WORKDIR}/bootmenu.conf
	echo "#ip 192.168.1.10" >> ${WORKDIR}/bootmenu.conf
	echo "#netmask 255.255.255.0" >> ${WORKDIR}/bootmenu.conf
	echo "#gateway 192.168.1.1" >> ${WORKDIR}/bootmenu.conf
	echo "#dns 192.168.1.1" >> ${WORKDIR}/bootmenu.conf
	mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP ::
	mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_LINUX_1 ::
	mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_RECOVERY ::
	mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/bootmenu.conf ::
	cp ${WORKDIR}/bootoptions.img ${IMGDEPLOYDIR}/bootoptions.img
}
