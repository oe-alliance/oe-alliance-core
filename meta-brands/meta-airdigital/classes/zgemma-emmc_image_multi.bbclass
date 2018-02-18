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
BLOCK_SIZE = "512"
BLOCK_SECTOR = "2"

BOOT_PARTITION_OFFSET = "$(expr ${GPT_OFFSET} \+ ${GPT_SIZE})"
BOOT_PARTITION_SIZE = "3072"

KERNEL_PARTITION_OFFSET = "$(expr ${BOOT_PARTITION_OFFSET} \+ ${BOOT_PARTITION_SIZE})"
KERNEL_PARTITION_SIZE = "8192"

ROOTFS_PARTITION_OFFSET = "$(expr ${KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})"
ROOTFS_PARTITION_SIZE = "819200"

SECOND_KERNEL_PARTITION_OFFSET = "$(expr ${ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})"

SECOND_ROOTFS_PARTITION_OFFSET = "$(expr ${SECOND_KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})"

THIRD_KERNEL_PARTITION_OFFSET = "$(expr ${SECOND_ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})"

THIRD_ROOTFS_PARTITION_OFFSET = "$(expr ${THIRD_KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})"

FOURTH_KERNEL_PARTITION_OFFSET = "$(expr ${THIRD_ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})"

FOURTH_ROOTFS_PARTITION_OFFSET = "$(expr ${FOURTH_KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})"

SWAP_PARTITION_OFFSET = "$(expr ${FOURTH_ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})"

EMMC_IMAGE = "${IMGDEPLOYDIR}/${IMAGE_NAME}.emmc.img"
EMMC_IMAGE_SIZE = "3817472"

IMAGE_CMD_zgemma-emmc () {
    dd if=/dev/zero of=${EMMC_IMAGE} bs=${BLOCK_SIZE} count=0 seek=$(expr ${EMMC_IMAGE_SIZE} \* ${BLOCK_SECTOR})
    parted -s ${EMMC_IMAGE} mklabel gpt
    parted -s ${EMMC_IMAGE} unit KiB mkpart boot fat16 ${BOOT_PARTITION_OFFSET} $(expr ${BOOT_PARTITION_OFFSET} \+ ${BOOT_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel1 ${KERNEL_PARTITION_OFFSET} $(expr ${KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs1 ext4 ${ROOTFS_PARTITION_OFFSET} $(expr ${ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel2 ${SECOND_KERNEL_PARTITION_OFFSET} $(expr ${SECOND_KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs2 ext4 ${SECOND_ROOTFS_PARTITION_OFFSET} $(expr ${SECOND_ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel3 ${THIRD_KERNEL_PARTITION_OFFSET} $(expr ${THIRD_KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs3 ext4 ${THIRD_ROOTFS_PARTITION_OFFSET} $(expr ${THIRD_ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart kernel4 ${FOURTH_KERNEL_PARTITION_OFFSET} $(expr ${FOURTH_KERNEL_PARTITION_OFFSET} \+ ${KERNEL_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart rootfs4 ext4 ${FOURTH_ROOTFS_PARTITION_OFFSET} $(expr ${FOURTH_ROOTFS_PARTITION_OFFSET} \+ ${ROOTFS_PARTITION_SIZE})
    parted -s ${EMMC_IMAGE} unit KiB mkpart swap linux-swap ${SWAP_PARTITION_OFFSET} 100%
    dd if=/dev/zero of=${WORKDIR}/boot.img bs=${BLOCK_SIZE} count=$(expr ${BOOT_PARTITION_SIZE} \* ${BLOCK_SECTOR})
    mkfs.msdos -S 512 ${WORKDIR}/boot.img
    echo "boot emmcflash0.kernel1 'root=/dev/mmcblk0p3 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP
    echo "boot emmcflash0.kernel1 'root=/dev/mmcblk0p3 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP_1
    echo "boot emmcflash0.kernel2 'root=/dev/mmcblk0p5 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP_2
    echo "boot emmcflash0.kernel3 'root=/dev/mmcblk0p7 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP_3
    echo "boot emmcflash0.kernel4 'root=/dev/mmcblk0p9 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP_4
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_1 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_2 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_3 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_4 ::
    dd conv=notrunc if=${WORKDIR}/boot.img of=${EMMC_IMAGE} bs=${BLOCK_SIZE} seek=$(expr ${BOOT_PARTITION_OFFSET} \* ${BLOCK_SECTOR})
    dd conv=notrunc if=${DEPLOY_DIR_IMAGE}/zImage of=${EMMC_IMAGE} bs=${BLOCK_SIZE} seek=$(expr ${KERNEL_PARTITION_OFFSET} \* ${BLOCK_SECTOR})
    resize2fs ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ext4 ${ROOTFS_PARTITION_SIZE}k
    # Truncate on purpose
    dd if=${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ext4 of=${EMMC_IMAGE} bs=${BLOCK_SIZE} seek=$(expr ${ROOTFS_PARTITION_OFFSET} \* ${BLOCK_SECTOR}) count=$(expr ${IMAGE_ROOTFS_SIZE} \* ${BLOCK_SECTOR})
}