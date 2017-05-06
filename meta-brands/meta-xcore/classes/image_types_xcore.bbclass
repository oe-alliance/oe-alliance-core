inherit image_types

IMAGE_TYPEDEP_xcore-emmc = "ext4"

BOOTDD_VOLUME_ID ?= "boot"

IMAGE_DEPENDS_xcore-emmc = " \
    parted-native \
    dosfstools-native \
    mtools-native \
    virtual/kernel \
    "

GPT_OFFSET = "0"
GPT_SIZE = "1024"

BOOT_PARTITION_OFFSET = "$(expr ${GPT_OFFSET} \+ ${GPT_SIZE})"
BOOT_PARTITION_SIZE = "3072"

KERNEL_PARTITION_OFFSET = "$(expr ${BOOT_PARTITION_OFFSET} \+ ${BOOT_PARTITION_SIZE})"
KERNEL_PARTITION_SIZE = "8192"

ROOTFS_PARTITION_OFFSET = "$(expr ${KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})"
ROOTFS_PARTITION_SIZE = "819200"

SECOND_KERNEL_PARTITION_OFFSET = "$(expr ${ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})"

SECOND_ROOTFS_PARTITION_OFFSET = "$(expr ${SECOND_KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})"

THRID_KERNEL_PARTITION_OFFSET = "$(expr ${SECOND_ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})"

THRID_ROOTFS_PARTITION_OFFSET = "$(expr ${THRID_KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})"

FOURTH_KERNEL_PARTITION_OFFSET = "$(expr ${THRID_ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})"

FOURTH_ROOTFS_PARTITION_OFFSET = "$(expr ${FOURTH_KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})"

SWAP_PARTITION_OFFSET = "$(expr ${FOURTH_ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})"

EMMC_IMAGE = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.emmc"
EMMC_IMAGE_SIZE = "3817472"

IMAGE_CMD_xcore-emmc () {
    dd if=/dev/zero of=${EMMC_IMAGE} bs=1024 count=0 seek=${EMMC_IMAGE_SIZE}
    parted -s ${EMMC_IMAGE} mklabel gpt
    parted -s ${EMMC_IMAGE} unit KiB mkpart boot fat16 ${BOOT_PARTITION_OFFSET} $(expr ${BOOT_PARTITION_OFFSET} \+ ${BOOT_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} set 1 boot on
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel1 ${KERNEL_PARTITION_OFFSET} $(expr ${KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs1 ext2 ${ROOTFS_PARTITION_OFFSET} $(expr ${ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel2 ${SECOND_KERNEL_PARTITION_OFFSET} $(expr ${SECOND_KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs2 ext2 ${SECOND_ROOTFS_PARTITION_OFFSET} $(expr ${SECOND_ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel3 ${THRID_KERNEL_PARTITION_OFFSET} $(expr ${THRID_KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs3 ext2 ${THRID_ROOTFS_PARTITION_OFFSET} $(expr ${THRID_ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel4 ${FOURTH_KERNEL_PARTITION_OFFSET} $(expr ${FOURTH_KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs4 ext2 ${FOURTH_ROOTFS_PARTITION_OFFSET} $(expr ${FOURTH_ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart swap linux-swap ${SWAP_PARTITION_OFFSET} $(expr ${EMMC_IMAGE_SIZE} \- 1024)
    dd if=/dev/zero of=${WORKDIR}/boot.img bs=1024 count=${BOOT_PARTITION_SIZE}
    mkfs.msdos -n "${BOOTDD_VOLUME_ID}" -S 512 ${WORKDIR}/boot.img
    echo "setenv STARTUP \"boot emmcflash0.kernel1 'root=/dev/mmcblk1p3 rootfstype=ext4 rootwait'\"" > ${WORKDIR}/cmdline.txt
    echo "setenv STARTUP \"boot emmcflash0.kernel1 'root=/dev/mmcblk1p3 rootfstype=ext4 rootwait'\"" > ${WORKDIR}/STARTUP_1
    echo "setenv STARTUP \"boot emmcflash0.kernel2 'root=/dev/mmcblk1p5 rootfstype=ext4 rootwait'\"" > ${WORKDIR}/STARTUP_2
    echo "setenv STARTUP \"boot emmcflash0.kernel3 'root=/dev/mmcblk1p7 rootfstype=ext4 rootwait'\"" > ${WORKDIR}/STARTUP_3
    echo "setenv STARTUP \"boot emmcflash0.kernel4 'root=/dev/mmcblk1p9 rootfstype=ext4 rootwait'\"" > ${WORKDIR}/STARTUP_4
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/cmdline.txt ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_1 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_2 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_3 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_4 ::
    dd conv=notrunc if=${WORKDIR}/boot.img of=${EMMC_IMAGE} bs=1024 seek=${BOOT_PARTITION_OFFSET}
    dd conv=notrunc if=${DEPLOY_DIR_IMAGE}/zImage of=${EMMC_IMAGE} bs=1024 seek=${KERNEL_PARTITION_OFFSET}
    resize2fs ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ext4 ${ROOTFS_PARTITION_SIZE}k
    # Truncate on purpose
    dd if=${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ext4 of=${EMMC_IMAGE} bs=1024 seek=${ROOTFS_PARTITION_OFFSET} count=${IMAGE_ROOTFS_SIZE}
}