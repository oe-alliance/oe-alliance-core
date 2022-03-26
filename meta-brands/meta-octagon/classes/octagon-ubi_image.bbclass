inherit image_types

IMAGE_TYPEDEP_octagonubi = "ubi"

do_image_octagonubi[depends] = " \
    parted-native:do_populate_sysroot \
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    virtual/kernel:do_populate_sysroot \
    octagon-buildimage-native:do_populate_sysroot \
    "

IMAGE_CMD_octagonubi () {
    mkdir -p ${IMGDEPLOYDIR}/userdata
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs1
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs2
    cp -a ${IMAGE_ROOTFS}/* ${IMGDEPLOYDIR}/userdata/linuxrootfs1/
    mkfs.ubifs -F -d ${IMGDEPLOYDIR}/userdata -m 2048 -o ${IMGDEPLOYDIR}/rootfs.ubifs -e 126976 -c 3800
    echo \[ubifs\] > ${IMGDEPLOYDIR}/rootfs.ubicfg
    echo mode=ubi >> ${IMGDEPLOYDIR}/rootfs.ubicfg
    echo image=${IMGDEPLOYDIR}/rootfs.ubifs >> ${IMGDEPLOYDIR}/rootfs.ubicfg
    echo vol_id=0 >> ${IMGDEPLOYDIR}/rootfs.ubicfg
    echo vol_type=dynamic >> ${IMGDEPLOYDIR}/rootfs.ubicfg
    echo vol_name=ubifs >> ${IMGDEPLOYDIR}/rootfs.ubicfg
    echo vol_flags=autoresize >> ${IMGDEPLOYDIR}/rootfs.ubicfg
    ubinize -o ${IMGDEPLOYDIR}/${IMAGE_NAME}.ubi -m 2048 -p 0x20000 ${IMGDEPLOYDIR}/rootfs.ubicfg
}