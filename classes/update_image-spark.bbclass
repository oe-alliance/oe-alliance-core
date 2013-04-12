# Default to 1 GB images
UPDATEIMG_SIZE ?= "256"

# Boot partition volume id
BOOTDD_VOLUME_ID ?= "${MACHINE}"

# Use an ext2 by default as rootfs
UPDATEIMG_ROOTFS_TYPE ?= "ext2.gz"
UPDATEIMG_ROOTFS = "${IMAGE_NAME}.rootfs.${UPDATEIMG_ROOTFS_TYPE}"
upDATEIMG_KERNEL_NAME = "${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGE_BASE_NAME}.bin"

# TODO: Add the final image here
IMAGE_DEPENDS_spark-updt = " \
			parted-native \
			mtools-native \
			dosfstools-native \
			u-boot-mkimage-native \			
			virtual/kernel \
			"

# USB-pendrive image name
UPDATEIMG = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.spark-updt"


# Additional files and/or directories to be copied into the vfat partition from the IMAGE_ROOTFS.
FATPAYLOAD ?= ""

IMAGEDATESTAMP = "${@time.strftime('%Y.%m.%d',time.gmtime())}"

IMAGE_CMD_spark-updt  () {
	# Initialize sdcard image file
	dd if=/dev/zero of=${UPDATEIMG} bs=1 count=0 seek=$(expr 1000 \* 1000 \* ${UPDATEIMG_SIZE})

	# Create partition table
	parted -s ${UPDATEIMG} mklabel msdos
	# Create boot partition and mark it as bootable
	parted -s ${UPDATEIMG} mkpart primary fat32 1MiB 100% 
	parted -s ${UPDATEIMG} set 1 boot on

	# Create a vfat image with boot files
	BOOT_BLOCKS=$(LC_ALL=C parted -s ${UPDATEIMG} unit b print | awk '/ 1 / { print substr($4, 1, length($4 -1)) / 512 /2 }')
	mkfs.vfat -n "${BOOTDD_VOLUME_ID}" -S 512 -C ${WORKDIR}/boot.img $BOOT_BLOCKS

	if [ -n ${FATPAYLOAD} ] ; then
		echo "Copying payload into VFAT"
		for entry in ${FATPAYLOAD} ; do
				# add the || true to stop aborting on vfat issues like not supporting .~lock files
				mcopy -i ${WORKDIR}/boot.img -s -v ${IMAGE_ROOTFS}$entry :: || true
		done
	fi

	# Add stamp file
	echo "${IMAGE_NAME}-${IMAGEDATESTAMP}" > ${WORKDIR}/image-version-info
	mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}//image-version-info ::

	# Burn Partitions
	dd if=${WORKDIR}/boot.img of=${UPDATEIMG} conv=notrunc seek=1 bs=1M && sync && sync
}
