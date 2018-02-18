inherit image_types

IMAGE_TYPEDEP_zgemma-emmc = "ext4"

IMAGE_DEPENDS_zgemma-emmc = " \
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
ROOTFS_PARTITION_SIZE = "1048576"

STORAGE_PARTITION_OFFSET = "$(expr ${ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})"

EMMC_IMAGE = "${IMGDEPLOYDIR}/${IMAGE_NAME}.emmc.img"
EMMC_IMAGE_SIZE = "3817472"

IMAGE_CMD_zgemma-emmc () {
    dd if=/dev/zero of=${EMMC_IMAGE} bs=1024 count=0 seek=${EMMC_IMAGE_SIZE}
    parted -s ${EMMC_IMAGE} mklabel gpt
    parted -s ${EMMC_IMAGE} unit KiB mkpart boot fat16 ${BOOT_PARTITION_OFFSET} $(expr ${BOOT_PARTITION_OFFSET} \+ ${BOOT_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel1 ${KERNEL_PARTITION_OFFSET} $(expr ${KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs1 ext2 ${ROOTFS_PARTITION_OFFSET} $(expr ${ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart storage ext2 ${STORAGE_PARTITION_OFFSET} $(expr ${EMMC_IMAGE_SIZE} \- 1024)
    dd if=/dev/zero of=${WORKDIR}/boot.img bs=1024 count=${BOOT_PARTITION_SIZE}
    mkfs.msdos -S 512 ${WORKDIR}/boot.img
    echo "boot emmcflash0.kernel1 'root=/dev/mmcblk0p3 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP
    echo "boot emmcflash0.kernel1 'root=/dev/mmcblk0p3 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP_1
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_1 ::
    dd conv=notrunc if=${WORKDIR}/boot.img of=${EMMC_IMAGE} bs=1024 seek=${BOOT_PARTITION_OFFSET}
    dd conv=notrunc if=${DEPLOY_DIR_IMAGE}/zImage of=${EMMC_IMAGE} bs=1024 seek=${KERNEL_PARTITION_OFFSET}
    resize2fs ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ext4 ${ROOTFS_PARTITION_SIZE}k
    # Truncate on purpose
    dd if=${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ext4 of=${EMMC_IMAGE} bs=1024 seek=${ROOTFS_PARTITION_OFFSET} count=${IMAGE_ROOTFS_SIZE}
}
