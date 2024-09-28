inherit image_types

do_image_airdigitalemmc[depends] = " \
    e2fsprogs-native:do_populate_sysroot \
    gptfdisk-native:do_populate_sysroot \
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    virtual/kernel:do_populate_sysroot \
"

EMMC_IMAGE = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.emmc.img"
BLOCK_SIZE="512"
BLOCK_SECTOR="2"
BOOT_SIZE="3"
RESCUE_SIZE="16"
KERNEL_SIZE="8"
SWAP_SIZE="256"
USERDATA_SIZE="0"
BOOT_PARTITION_SIZE = "3072"
EMMC_IMAGE_SIZE="1048576"

IMAGE_CMD:airdigitalemmc () {
    mkdir -p ${IMGDEPLOYDIR}/userdata
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs1
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs2
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs3
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs4
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs5
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs6
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs7
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs8
    cp -fR --preserve=mode,links ${IMAGE_ROOTFS}/* ${IMGDEPLOYDIR}/userdata/linuxrootfs1/
    eval local COUNT=\"0\"
    eval local MIN_COUNT=\"60\"
    if [ $ROOTFS_SIZE -lt $MIN_COUNT ]; then
        eval COUNT=\"$MIN_COUNT\"
    fi
    dd if=/dev/zero of=${IMGDEPLOYDIR}/${IMAGE_NAME}.ext4 seek=${IMAGE_ROOTFS_SIZE} count=$COUNT bs=1024
    /usr/bin/fakeroot mkfs.ext4 -F -i 4096 ${IMGDEPLOYDIR}/${IMAGE_NAME}.ext4 -d ${IMGDEPLOYDIR}/userdata

    dd if=/dev/zero of=${EMMC_IMAGE} bs=${BLOCK_SIZE} count=0 seek=$(expr ${EMMC_IMAGE_SIZE} \* ${BLOCK_SECTOR})

    sgdisk -o ${EMMC_IMAGE}
    sgdisk -n 1::+${BOOT_SIZE}MiB -c 1:boot ${EMMC_IMAGE}
    sgdisk -n 2::+${RESCUE_SIZE}MiB -c 2:rescue ${EMMC_IMAGE}
    sgdisk -n 3::+${KERNEL_SIZE}MiB -c 3:linuxkernel1 ${EMMC_IMAGE}
    sgdisk -n 4::+${KERNEL_SIZE}MiB -c 4:linuxkernel2 ${EMMC_IMAGE}
    sgdisk -n 5::+${KERNEL_SIZE}MiB -c 5:linuxkernel3 ${EMMC_IMAGE}
    sgdisk -n 6::+${KERNEL_SIZE}MiB -c 6:linuxkernel4 ${EMMC_IMAGE}
    sgdisk -n 7::+${KERNEL_SIZE}MiB -c 7:linuxkernel5 ${EMMC_IMAGE}
    sgdisk -n 8::+${KERNEL_SIZE}MiB -c 8:linuxkernel6 ${EMMC_IMAGE}
    sgdisk -n 9::+${KERNEL_SIZE}MiB -c 9:linuxkernel7 ${EMMC_IMAGE}
    sgdisk -n 10::+${KERNEL_SIZE}MiB -c 10:linuxkernel8 ${EMMC_IMAGE}
    sgdisk -n 11::+${SWAP_SIZE}MiB -c 11:swap ${EMMC_IMAGE}
    sgdisk -n 12::0 -c 12:userdata ${EMMC_IMAGE}

    sgdisk --backup=${WORKDIR}/gpt-backup ${EMMC_IMAGE}
    sgdisk --load-backup=${WORKDIR}/gpt-backup ${EMMC_IMAGE}


    dd if=/dev/zero of=${WORKDIR}/boot.img bs=${BLOCK_SIZE} count=$(expr ${BOOT_PARTITION_SIZE} \* ${BLOCK_SECTOR})
    mkfs.msdos -S 512 ${WORKDIR}/boot.img
    echo "boot emmcflash0.linuxkernel1 'root=/dev/mmcblk0p12 rootsubdir=linuxrootfs1 kernel=/dev/mmcblk0p3 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP
    echo "boot emmcflash0.linuxkernel1 'root=/dev/mmcblk0p12 rootsubdir=linuxrootfs1 kernel=/dev/mmcblk0p3 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP_LINUX_1_BOXMODE_1
    echo "boot emmcflash0.linuxkernel2 'root=/dev/mmcblk0p12 rootsubdir=linuxrootfs2 kernel=/dev/mmcblk0p4 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP_LINUX_2_BOXMODE_1
    echo "boot emmcflash0.linuxkernel3 'root=/dev/mmcblk0p12 rootsubdir=linuxrootfs3 kernel=/dev/mmcblk0p5 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP_LINUX_3_BOXMODE_1
    echo "boot emmcflash0.linuxkernel4 'root=/dev/mmcblk0p12 rootsubdir=linuxrootfs4 kernel=/dev/mmcblk0p6 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP_LINUX_4_BOXMODE_1
    echo "boot emmcflash0.linuxkernel5 'root=/dev/mmcblk0p12 rootsubdir=linuxrootfs5 kernel=/dev/mmcblk0p7 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP_LINUX_5_BOXMODE_1
    echo "boot emmcflash0.linuxkernel6 'root=/dev/mmcblk0p12 rootsubdir=linuxrootfs6 kernel=/dev/mmcblk0p8 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP_LINUX_6_BOXMODE_1
    echo "boot emmcflash0.linuxkernel7 'root=/dev/mmcblk0p12 rootsubdir=linuxrootfs7 kernel=/dev/mmcblk0p9 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP_LINUX_7_BOXMODE_1
    echo "boot emmcflash0.linuxkernel8 'root=/dev/mmcblk0p12 rootsubdir=linuxrootfs8 kernel=/dev/mmcblk0p10 rw rootwait ${MACHINE}_4.boxmode=1'" > ${WORKDIR}/STARTUP_LINUX_8_BOXMODE_1

    echo "boot emmcflash0.linuxkernel1 'brcm_cma=520M@248M brcm_cma=192M@768M root=/dev/mmcblk0p12 rootsubdir=linuxrootfs1 kernel=/dev/mmcblk0p3 rw rootwait ${MACHINE}_4.boxmode=12'" > ${WORKDIR}/STARTUP_LINUX_1_BOXMODE_12
    echo "boot emmcflash0.linuxkernel2 'brcm_cma=520M@248M brcm_cma=192M@768M root=/dev/mmcblk0p12 rootsubdir=linuxrootfs2 kernel=/dev/mmcblk0p4 rw rootwait ${MACHINE}_4.boxmode=12'" > ${WORKDIR}/STARTUP_LINUX_2_BOXMODE_12
    echo "boot emmcflash0.linuxkernel3 'brcm_cma=520M@248M brcm_cma=192M@768M root=/dev/mmcblk0p12 rootsubdir=linuxrootfs3 kernel=/dev/mmcblk0p5 rw rootwait ${MACHINE}_4.boxmode=12'" > ${WORKDIR}/STARTUP_LINUX_3_BOXMODE_12
    echo "boot emmcflash0.linuxkernel4 'brcm_cma=520M@248M brcm_cma=192M@768M root=/dev/mmcblk0p12 rootsubdir=linuxrootfs4 kernel=/dev/mmcblk0p6 rw rootwait ${MACHINE}_4.boxmode=12'" > ${WORKDIR}/STARTUP_LINUX_4_BOXMODE_12
    echo "boot emmcflash0.linuxkernel5 'brcm_cma=520M@248M brcm_cma=192M@768M root=/dev/mmcblk0p12 rootsubdir=linuxrootfs5 kernel=/dev/mmcblk0p7 rw rootwait ${MACHINE}_4.boxmode=12'" > ${WORKDIR}/STARTUP_LINUX_5_BOXMODE_12
    echo "boot emmcflash0.linuxkernel6 'brcm_cma=520M@248M brcm_cma=192M@768M root=/dev/mmcblk0p12 rootsubdir=linuxrootfs6 kernel=/dev/mmcblk0p8 rw rootwait ${MACHINE}_4.boxmode=12'" > ${WORKDIR}/STARTUP_LINUX_6_BOXMODE_12
    echo "boot emmcflash0.linuxkernel7 'brcm_cma=520M@248M brcm_cma=192M@768M root=/dev/mmcblk0p12 rootsubdir=linuxrootfs7 kernel=/dev/mmcblk0p9 rw rootwait ${MACHINE}_4.boxmode=12'" > ${WORKDIR}/STARTUP_LINUX_7_BOXMODE_12
    echo "boot emmcflash0.linuxkernel8 'brcm_cma=520M@248M brcm_cma=192M@768M root=/dev/mmcblk0p12 rootsubdir=linuxrootfs8 kernel=/dev/mmcblk0p10 rw rootwait ${MACHINE}_4.boxmode=12'" > ${WORKDIR}/STARTUP_LINUX_8_BOXMODE_12

    echo "boot emmcflash0.recure 'root=/dev/mmcblk0p12 kernel=/dev/mmcblk0p2'" > ${WORKDIR}/STARTUP_RECOVERY
 
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_1_BOXMODE_1 ::   
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_2_BOXMODE_1 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_3_BOXMODE_1 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_4_BOXMODE_1 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_5_BOXMODE_1 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_6_BOXMODE_1 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_7_BOXMODE_1 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_8_BOXMODE_1 ::

    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_1_BOXMODE_12 ::   
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_2_BOXMODE_12 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_3_BOXMODE_12 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_4_BOXMODE_12 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_5_BOXMODE_12 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_6_BOXMODE_12 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_7_BOXMODE_12 ::
    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_LINUX_8_BOXMODE_12 ::

    mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}/STARTUP_RECOVERY ::

    START_BOOT=$(sgdisk -i 1 ${EMMC_IMAGE} | grep "First sector" | awk '{print $3}') 
    START_RESCUE=$(sgdisk -i 2 ${EMMC_IMAGE} | grep "First sector" | awk '{print $3}')
    START_KERNEL1=$(sgdisk -i 3 ${EMMC_IMAGE} | grep "First sector" | awk '{print $3}')
    START_USERDATA=$(sgdisk -i 12 ${EMMC_IMAGE} | grep "First sector" | awk '{print $3}')

    dd if=${WORKDIR}/boot.img of=${EMMC_IMAGE} bs=${BLOCK_SIZE} seek=${START_BOOT} conv=notrunc
    #dd if=${IMGDEPLOYDIR}/${IMAGE_NAME}.recure of=${EMMC_IMAGE} bs=${BLOCK_SIZE} seek=${START_RESCUE} conv=notrunc
    dd if=${DEPLOY_DIR_IMAGE}/zImage of=${EMMC_IMAGE} bs=${BLOCK_SIZE} seek=${START_KERNEL1} conv=notrunc
    dd if=${IMGDEPLOYDIR}/${IMAGE_NAME}.ext4 of=${EMMC_IMAGE} bs=${BLOCK_SIZE} seek=${START_USERDATA} conv=notrunc
}
