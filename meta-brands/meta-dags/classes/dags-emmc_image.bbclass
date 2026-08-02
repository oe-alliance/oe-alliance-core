inherit image_types

IMAGE_TYPEDEP:dagsemmc = "ext4"

do_image_dagsemmc[depends] = " \
    parted-native:do_populate_sysroot \
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    virtual/kernel:do_populate_sysroot \
    e2fsprogs-native:do_populate_sysroot \
    e2fsprogs-ext4sparse-native:do_populate_sysroot \
    android-tools-native:do_populate_sysroot \
    dags-buildimage-native:do_populate_sysroot \
    "

IMAGE_CMD:dagsemmc () {
    rm -rf ${IMGDEPLOYDIR}/rootfs
    rm -rf ${IMGDEPLOYDIR}/rootfs_sub
    rm -rf ${IMGDEPLOYDIR}/rootfs_sub.ext4

    mkdir -p ${IMGDEPLOYDIR}/rootfs
    mkdir -p ${IMGDEPLOYDIR}/rootfs/linuxrootfs1
    mkdir -p ${IMGDEPLOYDIR}/rootfs/linuxrootfs2
    mkdir -p ${IMGDEPLOYDIR}/rootfs/linuxrootfs3
    mkdir -p ${IMGDEPLOYDIR}/rootfs/linuxrootfs4
    mkdir -p ${IMGDEPLOYDIR}/rootfs/lost+found
    cp -fR --preserve=mode,links ${IMAGE_ROOTFS}/* ${IMGDEPLOYDIR}/rootfs/linuxrootfs1/

    eval local COUNT=\"0\"
    eval local MIN_COUNT=\"60\"
    if [ $ROOTFS_SIZE -lt $MIN_COUNT ]; then
        eval COUNT=\"$MIN_COUNT\"
    fi

    truncate -s 6968M ${IMGDEPLOYDIR}/${IMAGE_NAME}_subrootfs.ext4.raw
    mkfs.ext4 -F -O ^has_journal -m 0 \
        -d ${IMGDEPLOYDIR}/rootfs \
        ${IMGDEPLOYDIR}/${IMAGE_NAME}_subrootfs.ext4.raw
    ext2simg_android \
        ${IMGDEPLOYDIR}/${IMAGE_NAME}_subrootfs.ext4.raw \
        ${IMGDEPLOYDIR}/${IMAGE_NAME}_subrootfs.ext4
    rm ${IMGDEPLOYDIR}/${IMAGE_NAME}_subrootfs.ext4.raw
    rm -rf ${IMGDEPLOYDIR}/rootfs
}
