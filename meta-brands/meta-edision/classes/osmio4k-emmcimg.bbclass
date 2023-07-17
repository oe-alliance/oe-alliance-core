inherit image_types

IMAGE_TYPEDEP:emmcimg = "ext4"

BOOTDD_VOLUME_ID ?= "boot"

do_image_emmcimg[depends] += "\
    parted-native:do_populate_sysroot\
    dosfstools-native:do_populate_sysroot\
    mtools-native:do_populate_sysroot\
    virtual/kernel:do_populate_sysroot\
    "

IMAGE_ROOTFS_ALIGNMENT = "1024"
BOOT_PARTITION_SIZE = "3072"
KERNEL_PARTITION_SIZE = "8192"
ROOTFS_PARTITION_SIZE = "1767424"

KERNEL1_PARTITION_OFFSET = "$(expr ${IMAGE_ROOTFS_ALIGNMENT} + ${BOOT_PARTITION_SIZE})"
ROOTFS1_PARTITION_OFFSET = "$(expr ${KERNEL1_PARTITION_OFFSET} + ${KERNEL_PARTITION_SIZE})"
KERNEL2_PARTITION_OFFSET = "$(expr ${ROOTFS1_PARTITION_OFFSET} + ${ROOTFS_PARTITION_SIZE})"
ROOTFS2_PARTITION_OFFSET = "$(expr ${KERNEL2_PARTITION_OFFSET} + ${KERNEL_PARTITION_SIZE})"
KERNEL3_PARTITION_OFFSET = "$(expr ${ROOTFS2_PARTITION_OFFSET} + ${ROOTFS_PARTITION_SIZE})"
ROOTFS3_PARTITION_OFFSET = "$(expr ${KERNEL3_PARTITION_OFFSET} + ${KERNEL_PARTITION_SIZE})"
KERNEL4_PARTITION_OFFSET = "$(expr ${ROOTFS3_PARTITION_OFFSET} + ${ROOTFS_PARTITION_SIZE})"
ROOTFS4_PARTITION_OFFSET = "$(expr ${KERNEL4_PARTITION_OFFSET} + ${KERNEL_PARTITION_SIZE})"
SWAP_PARTITION_OFFSET = "$(expr ${ROOTFS4_PARTITION_OFFSET} + ${ROOTFS_PARTITION_SIZE})"

EMMC_IMAGE = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.emmc"
EMMC_IMAGE_SIZE = "7634944"

IMAGE_CMD:emmcimg () {
    dd if=/dev/zero of=${EMMC_IMAGE} bs=1 count=0 seek=$(expr ${EMMC_IMAGE_SIZE} \* 1024)
    parted -s ${EMMC_IMAGE} mklabel gpt
    parted -s ${EMMC_IMAGE} unit KiB mkpart boot fat16 ${IMAGE_ROOTFS_ALIGNMENT} $(expr ${IMAGE_ROOTFS_ALIGNMENT} + ${BOOT_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} set 1 boot on
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel1 ${KERNEL1_PARTITION_OFFSET} $(expr ${KERNEL1_PARTITION_OFFSET} + ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs1 ext4 ${ROOTFS1_PARTITION_OFFSET} $(expr ${ROOTFS1_PARTITION_OFFSET} + ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel2 ${KERNEL2_PARTITION_OFFSET} $(expr ${KERNEL2_PARTITION_OFFSET} + ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs2 ext4 ${ROOTFS2_PARTITION_OFFSET} $(expr ${ROOTFS2_PARTITION_OFFSET} + ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel3 ${KERNEL3_PARTITION_OFFSET} $(expr ${KERNEL3_PARTITION_OFFSET} + ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs3 ext4 ${ROOTFS3_PARTITION_OFFSET} $(expr ${ROOTFS3_PARTITION_OFFSET} + ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel4 ${KERNEL4_PARTITION_OFFSET} $(expr ${KERNEL4_PARTITION_OFFSET} + ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs4 ext4 ${ROOTFS4_PARTITION_OFFSET} $(expr ${ROOTFS4_PARTITION_OFFSET} + ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart swap linux-swap ${SWAP_PARTITION_OFFSET} 100%
    dd if=/dev/zero of=${WORKDIR}/boot.img bs=1024 count=${BOOT_PARTITION_SIZE}
    mkfs.msdos -n "${BOOTDD_VOLUME_ID}" -S 512 ${WORKDIR}/boot.img
    echo "setenv STARTUP \"boot emmcflash0.kernel1 'root=/dev/mmcblk1p3 rootfstype=ext4 rootwait'\"" > ${WORKDIR}/STARTUP
    echo "setenv STARTUP \"boot emmcflash0.kernel1 'root=/dev/mmcblk1p3 rootfstype=ext4 rootwait'\"" > ${WORKDIR}/STARTUP_1
    echo "setenv STARTUP \"boot emmcflash0.kernel2 'root=/dev/mmcblk1p5 rootfstype=ext4 rootwait'\"" > ${WORKDIR}/STARTUP_2
    echo "setenv STARTUP \"boot emmcflash0.kernel3 'root=/dev/mmcblk1p7 rootfstype=ext4 rootwait'\"" > ${WORKDIR}/STARTUP_3
    echo "setenv STARTUP \"boot emmcflash0.kernel4 'root=/dev/mmcblk1p9 rootfstype=ext4 rootwait'\"" > ${WORKDIR}/STARTUP_4
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_1 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_2 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_3 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_4 ::
    dd conv=notrunc if=${WORKDIR}/boot.img of=${EMMC_IMAGE} seek=1 bs=$(expr ${IMAGE_ROOTFS_ALIGNMENT} \* 1024)
    dd conv=notrunc if=${DEPLOY_DIR_IMAGE}/zImage of=${EMMC_IMAGE} seek=1 bs=$(expr ${IMAGE_ROOTFS_ALIGNMENT} \* 1024 + ${BOOT_PARTITION_SIZE} \* 1024)
    dd if=${IMGDEPLOYDIR}/${IMAGE_NAME}.ext4 of=${EMMC_IMAGE} seek=1 bs=$(expr ${IMAGE_ROOTFS_ALIGNMENT} \* 1024 + ${BOOT_PARTITION_SIZE} \* 1024 + ${KERNEL_PARTITION_SIZE} \* 1024)
}
