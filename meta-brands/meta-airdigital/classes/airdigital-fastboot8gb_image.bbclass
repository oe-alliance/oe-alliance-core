inherit image_types

IMAGE_ROOTFS = "${WORKDIR}/rootfs/linuxrootfs1"
BOOTOPTIONS_PARTITION_SIZE = "2048"
IMAGE_ROOTFS_SIZE = "460800"
IMAGE_CMD:zgemmafastboot8gb[vardepsexclude] += "DATE DATETIME"

do_image_zgemmafastboot8gb[depends] = " \
	e2fsprogs-native:do_populate_sysroot \
	android-tools-native:do_populate_sysroot \
	dosfstools-native:do_populate_sysroot \
	mtools-native:do_populate_sysroot \
"

IMAGE_CMD:zgemmafastboot8gb () {
	eval local COUNT=\"0\"
	eval local MIN_COUNT=\"60\"
	if [ $ROOTFS_SIZE -lt $MIN_COUNT ]; then
		eval COUNT=\"$MIN_COUNT\"
	fi
	dd if=/dev/zero of=${IMGDEPLOYDIR}/${IMAGE_NAME}.ext4 seek=${IMAGE_ROOTFS_SIZE} count=$COUNT bs=1024
	mkfs.ext4 -F ${IMGDEPLOYDIR}/${IMAGE_NAME}.ext4 -d ${WORKDIR}/rootfs
	dd if=/dev/zero of=${WORKDIR}/bootoptions.img bs=1024 count=${BOOTOPTIONS_PARTITION_SIZE}
	mkfs.msdos -S 512 ${WORKDIR}/bootoptions.img
	echo "bootcmd=setenv notee y; setenv bootargs \$(bootargs) \$(bootargs_common); mmc read 0 0x1000000 0x3BD000 0x8000; bootm 0x1000000; run bootcmd_fallback" > ${WORKDIR}/STARTUP
	echo "bootargs=root=/dev/mmcblk0p23 rootsubdir=linuxrootfs1 rootfstype=ext4 kernel=/dev/mmcblk0p19" >> ${WORKDIR}/STARTUP
	echo "bootcmd=setenv vfd_msg andr;setenv bootargs \$(bootargs) \$(bootargs_common); run bootcmd_android; run bootcmd_fallback" > ${WORKDIR}/STARTUP_ANDROID
	echo "bootargs=androidboot.hardware=bigfish androidboot.serialno=0123456789 androidboot.selinux=enforcing hbcomp=/dev/block/mmcblk0p13 root=/dev/mmcblk0p13 androidboot.dtbo_idx=0 init=/init skip_initramfs" >> ${WORKDIR}/STARTUP_ANDROID
	echo "bootcmd=setenv vfd_msg andr;setenv bootargs \$(bootargs) \$(bootargs_common); run bootcmd_android; run bootcmd_fallback" > ${WORKDIR}/STARTUP_ANDROID_DISABLE_LINUXSE
	echo "bootargs=androidboot.hardware=bigfish androidboot.serialno=0123456789 androidboot.selinux=disable hbcomp=/dev/block/mmcblk0p13 root=/dev/mmcblk0p13 androidboot.dtbo_idx=0 init=/init skip_initramfs" >> ${WORKDIR}/STARTUP_ANDROID_DISABLE_LINUXSE
	echo "bootcmd=setenv notee y; setenv bootargs \$(bootargs) \$(bootargs_common); mmc read 0 0x1000000 0x3BD000 0x8000; bootm 0x1000000; run bootcmd_fallback" > ${WORKDIR}/STARTUP_LINUX_1
	echo "bootargs=root=/dev/mmcblk0p23 rootsubdir=linuxrootfs1 rootfstype=ext4 kernel=/dev/mmcblk0p19" >> ${WORKDIR}/STARTUP_LINUX_1
	echo "bootcmd=setenv notee y; setenv bootargs \$(bootargs) \$(bootargs_common); mmc read 0 0x1000000 0x3C5000 0x8000; bootm 0x1000000; run bootcmd_fallback" > ${WORKDIR}/STARTUP_LINUX_2
	echo "bootargs=root=/dev/mmcblk0p23 rootsubdir=linuxrootfs2 rootfstype=ext4 kernel=/dev/mmcblk0p20" >> ${WORKDIR}/STARTUP_LINUX_2
	echo "bootcmd=setenv notee y; setenv bootargs \$(bootargs) \$(bootargs_common); mmc read 0 0x1000000 0x3CD000 0x8000; bootm 0x1000000; run bootcmd_fallback" > ${WORKDIR}/STARTUP_LINUX_3
	echo "bootargs=root=/dev/mmcblk0p23 rootsubdir=linuxrootfs3 rootfstype=ext4 kernel=/dev/mmcblk0p21" >> ${WORKDIR}/STARTUP_LINUX_3
	echo "bootcmd=setenv notee y; setenv bootargs \$(bootargs) \$(bootargs_common); mmc read 0 0x1000000 0x3D5000 0x8000; bootm 0x1000000; run bootcmd_fallback" > ${WORKDIR}/STARTUP_LINUX_4
	echo "bootargs=root=/dev/mmcblk0p23 rootsubdir=linuxrootfs4 rootfstype=ext4 kernel=/dev/mmcblk0p22" >> ${WORKDIR}/STARTUP_LINUX_4
	echo "bootcmd=setenv notee y; setenv bootargs \$(bootargs_common); mmc read 0 0x1000000 0x1000 0x9000; bootm 0x1000000" > ${WORKDIR}/STARTUP_RECOVERY
	echo "imageurl https://raw.githubusercontent.com/oe-alliance/bootmenu/master/${MACHINEBUILD}/images" > ${WORKDIR}/bootmenu.conf
	echo "updateurl http://updateurl.redirectme.net/cgi-bin/index.py" >> ${WORKDIR}/bootmenu.conf
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
	mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_ANDROID ::
	mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_ANDROID_DISABLE_LINUXSE ::
	mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_LINUX_1 ::
	mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_LINUX_2 ::
	mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_LINUX_3 ::
	mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_LINUX_4 ::
	mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_RECOVERY ::
	mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/bootmenu.conf ::
	cp ${WORKDIR}/bootoptions.img ${IMGDEPLOYDIR}/bootoptions.img
	echo boot-recovery > ${WORKDIR}/misc-boot.img
	cp ${WORKDIR}/misc-boot.img ${IMGDEPLOYDIR}/misc-boot.img
	ext2simg ${IMGDEPLOYDIR}/${IMAGE_NAME}.ext4 ${IMGDEPLOYDIR}/${IMAGE_NAME}.userdata.ext4
}
