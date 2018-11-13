inherit image_types

IMAGE_TYPEDEP_fastboot4gb = "ext4"

BOOTOPTIONS_PARTITION_SIZE = "4096"
IMAGE_ROOTFS_SIZE = "1048576"

do_image_fastboot4gb[depends] = " \
	android-tools-native:do_populate_sysroot \
	dosfstools-native:do_populate_sysroot \
	mtools-native:do_populate_sysroot \
	"

IMAGE_CMD_fastboot4gb () {
    dd if=/dev/zero of=${WORKDIR}/bootoptions.img bs=1024 count=${BOOTOPTIONS_PARTITION_SIZE}
    mkfs.msdos -S 512 ${WORKDIR}/bootoptions.img
    echo "bootcmd=mmc read 0 0x1000000 0x38000 0x8000; bootm 0x1000000 bootargs=console=ttyAMA0,115200 root=/dev/mmcblk0p12 rootfstype=ext4" > ${WORKDIR}/STARTUP
    echo "bootcmd=mmc read 0 0x1000000 0x38000 0x8000; bootm 0x1000000 bootargs=console=ttyAMA0,115200 root=/dev/mmcblk0p12 rootfstype=ext4" > ${WORKDIR}/STARTUP_RED
    echo "bootcmd=mmc read 0 0x1000000 0x240000 0x8000; bootm 0x1000000 bootargs=console=ttyAMA0,115200 root=/dev/mmcblk0p14 rootfstype=ext4" > ${WORKDIR}/STARTUP_GREEN
    echo "bootcmd=mmc read 0 0x1000000 0x38000 0x8000; bootm 0x1000000 bootargs=console=ttyAMA0,115200 root=/dev/mmcblk0p12 rootfstype=ext4" > ${WORKDIR}/STARTUP_YELLOW
    echo "bootcmd=mmc read 0 0x1000000 0x38000 0x8000; bootm 0x1000000 bootargs=console=ttyAMA0,115200 root=/dev/mmcblk0p12 rootfstype=ext4" > ${WORKDIR}/STARTUP_BLUE
    mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP ::
    mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_RED ::
    mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_GREEN ::
    mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_YELLOW ::
    mcopy -i ${WORKDIR}/bootoptions.img -v ${WORKDIR}/STARTUP_BLUE ::
    cp ${WORKDIR}/bootoptions.img ${IMGDEPLOYDIR}/bootoptions.img
    ext2simg -zv ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext4 ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.fastboot4gb.gz
}