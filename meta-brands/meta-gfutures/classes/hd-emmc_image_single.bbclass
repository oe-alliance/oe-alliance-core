inherit image_types

IMAGE_TYPEDEP_hd-emmc = "ext4"

IMAGE_DEPENDS_hd-emmc = " \
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

EMMC_IMAGE = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.emmc.img"
EMMC_IMAGE_SIZE = "3817472"

IMAGE_CMD_hd-emmc () {
    dd if=/dev/zero of=${EMMC_IMAGE} bs=1024 count=0 seek=${EMMC_IMAGE_SIZE}
    parted -s ${EMMC_IMAGE} mklabel gpt
    parted -s ${EMMC_IMAGE} unit KiB mkpart boot fat16 ${BOOT_PARTITION_OFFSET} $(expr ${BOOT_PARTITION_OFFSET} \+ ${BOOT_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel1 ${KERNEL_PARTITION_OFFSET} $(expr ${KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs1 ext2 ${ROOTFS_PARTITION_OFFSET} $(expr ${ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart storage ext2 ${STORAGE_PARTITION_OFFSET} $(expr ${EMMC_IMAGE_SIZE} \- 1024)
    dd if=/dev/zero of=${WORKDIR}/boot.img bs=1024 count=${BOOT_PARTITION_SIZE}
    mkfs.msdos -S 512 ${WORKDIR}/boot.img
    if [ "${MACHINEBUILD}" = 'bre2ze4k' ]; then
        echo "boot emmcflash0.kernel1 'root=/dev/mmcblk0p2 rw rootwait usbcore.old_scheme_first=1 ${MACHINE}_4.boxmode=12'" > ${WORKDIR}/STARTUP_1
    else
        echo "boot emmcflash0.kernel1 'root=/dev/mmcblk0p2 rw rootwait ${MACHINE}_4.boxmode=12'" > ${WORKDIR}/STARTUP_1
    fi
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_1 ::
    dd conv=notrunc if=${WORKDIR}/boot.img of=${EMMC_IMAGE} bs=1024 seek=${BOOT_PARTITION_OFFSET}
    dd conv=notrunc if=${DEPLOY_DIR_IMAGE}/zImage of=${EMMC_IMAGE} bs=1024 seek=${KERNEL_PARTITION_OFFSET}
    resize2fs ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ext4 ${ROOTFS_PARTITION_SIZE}k
    # Truncate on purpose
    dd if=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ext4 of=${EMMC_IMAGE} bs=1024 seek=${ROOTFS_PARTITION_OFFSET} count=${IMAGE_ROOTFS_SIZE}
}
