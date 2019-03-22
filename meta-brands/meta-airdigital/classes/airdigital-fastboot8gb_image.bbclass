inherit image_types

IMAGE_TYPEDEP_fastboot8gb = "ext4"

BOOTOPTIONS_PARTITION_SIZE = "2048"
IMAGE_ROOTFS_SIZE = "786432"

do_image_fastboot8gb[depends] = " \
	android-tools-native:do_populate_sysroot \
	dosfstools-native:do_populate_sysroot \
	mtools-native:do_populate_sysroot \
	"

IMAGE_CMD_fastboot8gb () {
    dd if=/dev/zero of=${WORKDIR}/bootoptions.img bs=1024 count=${BOOTOPTIONS_PARTITION_SIZE}
    mkfs.msdos -S 512 ${WORKDIR}/bootoptions.img
    echo "bootcmd=mmc read 0 0x1000000 0x3BD000 0x8000; bootm 0x1000000 bootargs=console=ttyAMA0,115200 root=/dev/mmcblk0p19  rootfstype=ext4" > ${WORKDIR}/STARTUP
    echo "bootcmd=mmc read 0 0x3F000000 0x3A000 0x4000; bootm 0x3F000000; mmc read 0 0x1FFBFC0 0x26000 0xC800; bootargs=androidboot.selinux=enforcing androidboot.serialno=0123456789 console=ttyAMA0,115200" > ${WORKDIR}/STARTUP_ANDROID
    echo "bootcmd=mmc read 0 0x1000000 0x3BD000 0x8000; bootm 0x1000000 bootargs=console=ttyAMA0,115200 root=/dev/mmcblk0p19  rootfstype=ext4" > ${WORKDIR}/STARTUP_LINUX_1
    echo "bootcmd=mmc read 0 0x1000000 0x545000 0x8000; bootm 0x1000000 bootargs=console=ttyAMA0,115200 root=/dev/mmcblk0p23  rootfstype=ext4" > ${WORKDIR}/STARTUP_LINUX_2
    echo "bootcmd=mmc read 0 0x1000000 0x54D000 0x8000; bootm 0x1000000 bootargs=console=ttyAMA0,115200 root=/dev/mmcblk0p23  rootfstype=ext4" > ${WORKDIR}/STARTUP_LINUX_3
    echo "bootcmd=mmc read 0 0x1000000 0x555000 0x8000; bootm 0x1000000 bootargs=console=ttyAMA0,115200 root=/dev/mmcblk0p23  rootfstype=ext4" > ${WORKDIR}/STARTUP_LINUX_4
    echo "bootcmd=mmc read 0 0x1000000 0x1000 0x9000; bootm 0x1000000" > ${WORKDIR}/STARTUP_RECOVERY
    mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP ::
    mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_ANDROID ::
    mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_LINUX_1 ::
    mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_LINUX_2 ::
    mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_LINUX_3 ::
    mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_LINUX_4 ::
    mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_RECOVERY ::
    cp ${WORKDIR}/bootoptions.img ${IMGDEPLOYDIR}/bootoptions.img
    ext2simg -zv ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext4 ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.fastboot8gb.gz
}