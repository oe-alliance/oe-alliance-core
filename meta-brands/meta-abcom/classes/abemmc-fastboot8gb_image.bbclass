inherit image_types

IMAGE_TYPEDEP:abfastboot8gb = "ext4 tar"
BOOTOPTIONS_PARTITION_SIZE = "2048"

do_image_abfastboot8gb[vardepsexclude] = "DATE DATETIME"

do_image_abfastboot8gb[depends] = " \
	e2fsprogs-native:do_populate_sysroot \
	android-tools-native:do_populate_sysroot \
	dosfstools-native:do_populate_sysroot \
	mtools-native:do_populate_sysroot \
"

IMAGE_CMD:abfastboot8gb () {
    dd if=/dev/zero of=${WORKDIR}/bootoptions.img bs=1024 count=${BOOTOPTIONS_PARTITION_SIZE}
    mkfs.msdos -S 512 ${WORKDIR}/bootoptions.img
    echo "bootcmd=setenv bootargs \$(bootargs) \$(bootargs_common); mmc read 0 0x1000000 0x3BD000 0x8000; bootm 0x1000000; run bootcmd_fallback" > ${WORKDIR}/STARTUP
    echo "bootargs=root=/dev/mmcblk0p23 rootsubdir=linuxrootfs1 rootfstype=ext4 kernel=/dev/mmcblk0p19" >> ${WORKDIR}/STARTUP
    echo "bootcmd=setenv vfd_msg andr;setenv bootargs \$(bootargs) \$(bootargs_common); run bootcmd_android; run bootcmd_fallback" > ${WORKDIR}/STARTUP_ANDROID
    echo "bootargs=androidboot.selinux=enforcing androidboot.serialno=0123456789" >> ${WORKDIR}/STARTUP_ANDROID
    echo "bootcmd=setenv vfd_msg andr;setenv bootargs \$(bootargs) \$(bootargs_common); run bootcmd_android; run bootcmd_fallback" > ${WORKDIR}/STARTUP_ANDROID_DISABLE_LINUXSE
    echo "bootargs=androidboot.selinux=disable androidboot.serialno=0123456789" >> ${WORKDIR}/STARTUP_ANDROID_DISABLE_LINUXSE
    echo "bootcmd=setenv bootargs \$(bootargs) \$(bootargs_common); mmc read 0 0x1000000 0x3BD000 0x8000; bootm 0x1000000; run bootcmd_fallback" > ${WORKDIR}/STARTUP_LINUX_1
    echo "bootargs=root=/dev/mmcblk0p23 rootsubdir=linuxrootfs1 rootfstype=ext4 kernel=/dev/mmcblk0p19" >> ${WORKDIR}/STARTUP_LINUX_1
    echo "bootcmd=setenv bootargs \$(bootargs) \$(bootargs_common); mmc read 0 0x1000000 0x3C5000 0x8000; bootm 0x1000000; run bootcmd_fallback" > ${WORKDIR}/STARTUP_LINUX_2
    echo "bootargs=root=/dev/mmcblk0p23 rootsubdir=linuxrootfs2 rootfstype=ext4 kernel=/dev/mmcblk0p20" >> ${WORKDIR}/STARTUP_LINUX_2
    echo "bootcmd=setenv bootargs \$(bootargs) \$(bootargs_common); mmc read 0 0x1000000 0x3CD000 0x8000; bootm 0x1000000; run bootcmd_fallback" > ${WORKDIR}/STARTUP_LINUX_3
    echo "bootargs=root=/dev/mmcblk0p23 rootsubdir=linuxrootfs3 rootfstype=ext4 kernel=/dev/mmcblk0p21" >> ${WORKDIR}/STARTUP_LINUX_3
    echo "bootcmd=setenv bootargs \$(bootargs) \$(bootargs_common); mmc read 0 0x1000000 0x3D5000 0x8000; bootm 0x1000000; run bootcmd_fallback" > ${WORKDIR}/STARTUP_LINUX_4
    echo "bootargs=root=/dev/mmcblk0p23 rootsubdir=linuxrootfs4 rootfstype=ext4 kernel=/dev/mmcblk0p22" >> ${WORKDIR}/STARTUP_LINUX_4
    echo "bootcmd=setenv bootargs \$(bootargs_common); mmc read 0 0x1000000 0x1000 0x9000; bootm 0x1000000" > ${WORKDIR}/STARTUP_RECOVERY
    echo "imageurl https://raw.githubusercontent.com/oe-alliance/bootmenu/master/${MACHINE}/images" > ${WORKDIR}/bootmenu.conf
    echo "updateurl http://updateurl.ddns.net/cgi-bin/index.py" >> ${WORKDIR}/bootmenu.conf
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
}
