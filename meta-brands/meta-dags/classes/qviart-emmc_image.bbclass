inherit image_types

IMAGE_TYPEDEP_qviartemmc = "ext4"

do_image_qviartemmc[depends] = " \
    parted-native:do_populate_sysroot \
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    virtual/kernel:do_populate_sysroot \
    android-tools-native:do_populate_sysroot \
    dags-buildimage-native:do_populate_sysroot \
    "

IMAGE_CMD_qviartemmc () {
  rm -rf ${IMGDEPLOYDIR}/rootfs
  rm -rf ${IMGDEPLOYDIR}/rootfs_sub
  rm -rf ${IMGDEPLOYDIR}/rootfs_sub.ext4

  mkdir -p ${IMGDEPLOYDIR}/rootfs
	mkdir -p ${IMGDEPLOYDIR}/rootfs/linuxrootfs1
	mkdir -p ${IMGDEPLOYDIR}/rootfs/linuxrootfs2
	mkdir -p ${IMGDEPLOYDIR}/rootfs/linuxrootfs3
	mkdir -p ${IMGDEPLOYDIR}/rootfs/linuxrootfs4
  mkdir -p ${IMGDEPLOYDIR}/rootfs/lost+found
	cp -a ${IMAGE_ROOTFS}/* ${IMGDEPLOYDIR}/rootfs/linuxrootfs1/

	eval local COUNT=\"0\"
	eval local MIN_COUNT=\"60\"
	if [ $ROOTFS_SIZE -lt $MIN_COUNT ]; then
		eval COUNT=\"$MIN_COUNT\"
	fi

	make_ext4fs -s -l 6968M ${IMGDEPLOYDIR}/${IMAGE_NAME}_subrootfs.ext4 ${IMGDEPLOYDIR}/rootfs
}
