inherit image_types

IMAGE_TYPEDEP_dinobotemmcmultiboot = "ext4"

do_image_dinobotemmcmultiboot[depends]  = " \
    parted-native:do_populate_sysroot \
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    virtual/kernel:do_populate_sysroot \
    "

IMAGE_CMD_dinobotemmcmultiboot () {
    mkdir -p ${IMGDEPLOYDIR}/userdata
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs1
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs2
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs3
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs4
    cp -fR --preserve=mode,links ${IMAGE_ROOTFS}/* ${IMGDEPLOYDIR}/userdata/linuxrootfs1/
    eval local COUNT=\"0\"
    eval local MIN_COUNT=\"60\"
    if [ $ROOTFS_SIZE -lt $MIN_COUNT ]; then
        eval COUNT=\"$MIN_COUNT\"
    fi
    dd if=/dev/zero of=${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ext4 seek=${IMAGE_ROOTFS_SIZE} count=$COUNT bs=1024
    /usr/bin/fakeroot mkfs.ext4 -F -i 4096 ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ext4 -d ${IMGDEPLOYDIR}/userdata
}

