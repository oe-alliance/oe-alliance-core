IMAGE_CMD_jffs2nfi = " \
	mkfs.jffs2 \
		--root=${IMAGE_ROOTFS}/boot \
		--disable-compressor=lzo \
		--compression-mode=size \
		--output=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
		${EXTRA_IMAGECMD}; \
	rm -rf ${IMAGE_ROOTFS}/boot/*; \
	printf '/dev/mtdblock2\t/boot\t\tjffs2\tro\t\t\t\t0 0\n' >> ${IMAGE_ROOTFS}/etc/fstab; \
	mkfs.jffs2 \
		--root=${IMAGE_ROOTFS} \
		--disable-compressor=lzo \
		--compression-mode=size \
		--output=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 \
		${EXTRA_IMAGECMD}; \
	${DREAMBOX_BUILDIMAGE} \
		--boot-partition ${DREAMBOX_PART0_SIZE}:${DEPLOY_DIR_IMAGE}/secondstage-${MACHINE}.bin \
		--data-partition ${DREAMBOX_PART1_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
		--data-partition ${DREAMBOX_PART2_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 \
		> ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi; \
"

IMAGE_CMD_ubinfi = " \
	mkfs.jffs2 \
		--root=${IMAGE_ROOTFS}/boot \
		--disable-compressor=lzo \
		--compression-mode=size \
		--output=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
		${EXTRA_IMAGECMD}; \
	rm -rf ${IMAGE_ROOTFS}/boot/*; \
	printf '/dev/mtdblock2\t/boot\t\tjffs2\tro\t\t\t\t0 0\n' >> ${IMAGE_ROOTFS}/etc/fstab; \
	echo \[root\] > ubinize.cfg; \
	echo mode=ubi >> ubinize.cfg; \
	echo image=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs >> ubinize.cfg; \
	echo vol_id=0 >> ubinize.cfg; \
	echo vol_name=${UBI_VOLNAME} >> ubinize.cfg; \
	echo vol_type=dynamic >> ubinize.cfg; \
	if [ ${UBINIZE_VOLSIZE} = "0" ]; then \
		echo vol_flags=autoresize >> ubinize.cfg; \
	else \
		echo vol_size=${UBINIZE_VOLSIZE} >> ubinize.cfg; \
		if [ ${UBINIZE_DATAVOLSIZE} != "0" ]; then \
			echo \[data\] >> ubinize.cfg; \
			echo mode=ubi >> ubinize.cfg; \
			echo vol_id=1 >> ubinize.cfg; \
			echo vol_type=dynamic >> ubinize.cfg; \
			echo vol_name=data >> ubinize.cfg; \
			echo vol_size=${UBINIZE_DATAVOLSIZE} >> ubinize.cfg; \
			echo vol_flags=autoresize >> ubinize.cfg; \
			printf '/dev/ubi0_1\t/data\t\tubifs\trw,nofail\t\t\t\t0 0\n' >> ${IMAGE_ROOTFS}/etc/fstab; \
			install -d ${IMAGE_ROOTFS}/data; \
		fi; \
	fi; \
	mkfs.ubifs \
		-r ${IMAGE_ROOTFS} \
		-o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs \
		${MKUBIFS_ARGS}; \
	ubinize -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${UBINIZE_ARGS} ubinize.cfg; \
	${DREAMBOX_BUILDIMAGE} \
		--boot-partition ${DREAMBOX_PART0_SIZE}:${DEPLOY_DIR_IMAGE}/secondstage-${MACHINE}.bin \
		--data-partition ${DREAMBOX_PART1_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
		--data-partition ${DREAMBOX_PART2_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi \
		> ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi; \
"

EXTRA_IMAGECMD_jffs2nfi ?= "-e ${DREAMBOX_ERASE_BLOCK_SIZE} -n -l"
EXTRA_IMAGECMD_ubinfi ?= "-e ${DREAMBOX_ERASE_BLOCK_SIZE} -n -l"

do_image_jffs2nfi[depends] += "mtd-utils-native:do_populate_sysroot dreambox-buildimage-native:do_populate_sysroot"
do_image_ubinfi[depends] += "mtd-utils-native:do_populate_sysroot dreambox-buildimage-native:do_populate_sysroot"

IMAGE_TYPES += "jffs2nfi ubinfi"