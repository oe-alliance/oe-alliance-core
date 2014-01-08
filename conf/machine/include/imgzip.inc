# image name, used for multi boot selection
MULTIBOOT_IMAGE_NAME ?= "${IMAGE_BASENAME}"

# a rootfs size of 192MB should be sufficient (can be overridden by an image)
ROOTFS_SIZE ?= "C000000"

UBI_VOLNAME = "rootfs"

# image name in the zipfile, can be overridden by an image
ZIPFILE_IMAGE_NAME ?= "firmware.img"

EXTRA_IMAGEDEPENDS += "createimage-${MACHINE}"

IMAGE_CMD_imgzip256 = " \
	mkfs.ubifs -r ${IMAGE_ROOTFS} -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs_256.ubifs -m 2048 -e 126976 -c 4096; \
	mkdir -p ${DEPLOY_DIR_IMAGE}/flash-image-256; \
	createimage_${MACHINE} 126976 ${MULTIBOOT_IMAGE_NAME} kernel ${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin 0 0 rootfs ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs_256.ubifs ${ROOTFS_SIZE} 1 > ${DEPLOY_DIR_IMAGE}/flash-image-256/${ZIPFILE_IMAGE_NAME}; \
	cd ${DEPLOY_DIR_IMAGE}; \
	zip ${IMAGE_NAME}-256MB.zip flash-image-256/*; \
	rm -Rf flash-image-256; \
	"

IMAGE_CMD_imgzip1024 = " \
	mkfs.ubifs -r ${IMAGE_ROOTFS} -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs_1024.ubifs -m 4096 -e 253952 -c 4096; \
	mkdir -p ${DEPLOY_DIR_IMAGE}/flash-image-1024; \
	createimage_${MACHINE} 253952 ${MULTIBOOT_IMAGE_NAME} kernel ${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin 0 0 rootfs ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs_1024.ubifs ${ROOTFS_SIZE} 1 > ${DEPLOY_DIR_IMAGE}/flash-image-1024/${ZIPFILE_IMAGE_NAME}; \
	cd ${DEPLOY_DIR_IMAGE}; \
	zip ${IMAGE_NAME}-1024MB.zip flash-image-1024/*; \
	rm -Rf flash-image-1024; \
	"
