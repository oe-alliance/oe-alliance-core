inherit image_types

IMAGE_CMD_jffs2.nfi = " \
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
		--boot-partition ${DREAMBOX_PART0_SIZE}:${STAGING_DATADIR}/dreambox-secondstage/secondstage-${MACHINE}.bin \
		--data-partition ${DREAMBOX_PART1_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
		--data-partition ${DREAMBOX_PART2_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 \
		> ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi; \
"

IMAGE_CMD_sum.jffs2.nfi = " \
	mkfs.jffs2 \
		--root=${IMAGE_ROOTFS}/boot \
		--disable-compressor=lzo \
		--compression-mode=size \
		--output=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
		${EXTRA_IMAGECMD}; \
	sumtool \
		-i ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
		-o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.sum.jffs2 \
		${EXTRA_IMAGECMD}; \
	rm -rf ${IMAGE_ROOTFS}/boot/*; \
	printf '/dev/mtdblock2\t/boot\t\tjffs2\tro\t\t\t\t0 0\n' >> ${IMAGE_ROOTFS}/etc/fstab; \
	mkfs.jffs2 \
		--root=${IMAGE_ROOTFS} \
		--disable-compressor=lzo \
		--compression-mode=size \
		--output=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 \
		${EXTRA_IMAGECMD}; \
	sumtool \
		-i ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 \
		-o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.sum.jffs2 \
		${EXTRA_IMAGECMD}; \
	${DREAMBOX_BUILDIMAGE} \
		--boot-partition ${DREAMBOX_PART0_SIZE}:${STAGING_DATADIR}/dreambox-secondstage/secondstage-${MACHINE}.bin \
		--data-partition ${DREAMBOX_PART1_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.sum.jffs2 \
		--data-partition ${DREAMBOX_PART2_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.sum.jffs2 \
		> ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi; \
"

EXTRA_IMAGECMD_jffs2.nfi ?= "-e ${DREAMBOX_ERASE_BLOCK_SIZE} -n -l"
EXTRA_IMAGECMD_sum.jffs2.nfi ?= "-e ${DREAMBOX_ERASE_BLOCK_SIZE} -n -l"

IMAGE_DEPENDS_jffs2.nfi = "${IMAGE_DEPENDS_jffs2} dreambox-buildimage-native"
IMAGE_DEPENDS_sum.jffs2.nfi = "${IMAGE_DEPENDS_sum.jffs2} dreambox-buildimage-native"

IMAGE_TYPES += "jffs2.nfi sum.jffs2.nfi"
