inherit image_types

IMAGE_TYPEDEP:octagonubi = "ubi"

do_image_octagonubi[depends] = " \
    parted-native:do_populate_sysroot \
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    virtual/kernel:do_populate_sysroot \
    octagon-buildimage-native:do_populate_sysroot \
    "

IMAGE_CMD:octagonubi () {
    mkdir -p ${IMGDEPLOYDIR}/userdata
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs1
    mkdir -p ${IMGDEPLOYDIR}/userdata/linuxrootfs2
    cp -fR --preserve=mode,links ${IMAGE_ROOTFS}/* ${IMGDEPLOYDIR}/userdata/linuxrootfs1/
    mkfs.ubifs -F -d ${IMGDEPLOYDIR}/userdata -m 4096 -o ${IMGDEPLOYDIR}/rootfs.ubifs -e 253952 -c 1900
    echo \[ubifs\] > ${IMGDEPLOYDIR}/rootfs.ubicfg
    echo mode=ubi >> ${IMGDEPLOYDIR}/rootfs.ubicfg
    echo image=${IMGDEPLOYDIR}/rootfs.ubifs >> ${IMGDEPLOYDIR}/rootfs.ubicfg
    echo vol_id=0 >> ${IMGDEPLOYDIR}/rootfs.ubicfg
    echo vol_type=dynamic >> ${IMGDEPLOYDIR}/rootfs.ubicfg
    echo vol_name=ubifs >> ${IMGDEPLOYDIR}/rootfs.ubicfg
    echo vol_flags=autoresize >> ${IMGDEPLOYDIR}/rootfs.ubicfg
    ubinize -o ${IMGDEPLOYDIR}/${IMAGE_NAME}.ubi -m 4096 -p 0x40000 ${IMGDEPLOYDIR}/rootfs.ubicfg
}