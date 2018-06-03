inherit image_types

#
# Create an image that can by written onto a SD card using dd.
#
# The disk layout used is:
#
#    0                      -> IMAGE_ROOTFS_ALIGNMENT         - reserved for other data
#    IMAGE_ROOTFS_ALIGNMENT -> BOOT_SPACE                     - bootloader and kernel
#    BOOT_SPACE             -> SDIMG_SIZE                     - rootfs
#

#                                                     Default Free space = 1.3x
#                                                     Use IMAGE_OVERHEAD_FACTOR to add more space
#                                                     <--------->
#            4MiB                 SDIMG_ROOTFS
# <-----------------------> <---------------------->
#  ------------------------ ------------------------
# | IMAGE_ROOTFS_ALIGNMENT | ROOTFS_SIZE            |
#  ------------------------ ------------------------
# ^                        ^                        ^
# |                        |                        |
# 0                      4MiB   SDIMG_ROOTFS

# This image depends on the rootfs image
IMAGE_TYPEDEP_odroidcsdimg = "${SDIMG_ROOTFS_TYPE}"

# Set initramfs extension
KERNEL_INITRAMFS ?= ""

# Boot partition volume id
BOOTDD_VOLUME_ID ?= "odroid"

# Boot partition size [in KiB] (will be rounded up to IMAGE_ROOTFS_ALIGNMENT)
BOOT_SPACE ?= "20480"

# Set alignment to 4MB [in KiB]
IMAGE_ROOTFS_ALIGNMENT = "4096"

# Use an uncompressed ext4 by default as rootfs
SDIMG_ROOTFS_TYPE ?= "ext4"
SDIMG_ROOTFS = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.${SDIMG_ROOTFS_TYPE}"

do_image_odroidcsdimg[depends] += "parted-native:do_populate_sysroot dosfstools-native:do_populate_sysroot mtools-native:do_populate_sysroot virtual/kernel:do_populate_sysroot ${@bb.utils.contains("KERNEL_IMAGETYPE", "uImage:do_populate_sysroot", "u-boot:do_populate_sysroot", "",d)}"


# Amlogic Boot magic
BL1_SUFFIX ?= "bin.hardkernel"
BL1_SYMLINK ?= "bl1-${MACHINE}.${BL1_SUFFIX}"

# U-Boot
UBOOT_SUFFIX ?= "bin"
UBOOT_SYMLINK ?= "u-boot-${MACHINE}.${UBOOT_SUFFIX}"

# SD card image name
SDIMG = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.odroidc-sdimg"

IMAGE_CMD_odroidcsdimg () {

	# Align partitions
	BOOT_SPACE_ALIGNED=$(expr ${BOOT_SPACE} + ${IMAGE_ROOTFS_ALIGNMENT} - 1)
	BOOT_SPACE_ALIGNED=$(expr ${BOOT_SPACE_ALIGNED} - ${BOOT_SPACE_ALIGNED} % ${IMAGE_ROOTFS_ALIGNMENT})
	ROOTFS_SIZE=`du -bks ${SDIMG_ROOTFS} | awk '{print $1}'`
        # Round up RootFS size to the alignment size as well
	ROOTFS_SIZE_ALIGNED=$(expr ${ROOTFS_SIZE} + ${IMAGE_ROOTFS_ALIGNMENT} - 1)
	ROOTFS_SIZE_ALIGNED=$(expr ${ROOTFS_SIZE_ALIGNED} - ${ROOTFS_SIZE_ALIGNED} % ${IMAGE_ROOTFS_ALIGNMENT})
	SDIMG_SIZE=$(expr ${IMAGE_ROOTFS_ALIGNMENT} + ${BOOT_SPACE_ALIGNED} + ${ROOTFS_SIZE_ALIGNED})

	echo "Creating RootFS ${ROOTFS_SIZE_ALIGNED} KiB"

	# Initialize sdcard image file
	dd if=/dev/zero of=${SDIMG} bs=1024 count=0 seek=${SDIMG_SIZE}

	# Create partition table
	parted -s ${SDIMG} mklabel msdos
	# Create rootfs partition to the end of disk
	parted -s ${SDIMG} -- unit KiB mkpart primary ext2 ${IMAGE_ROOTFS_ALIGNMENT} -1s
	# Set boot flag
	parted -s ${SDIMG} set 1 boot on
	parted ${SDIMG} print

	# Burn amlogic's intial bootloader BL1
	dd if=${DEPLOY_DIR_IMAGE}/${BL1_SYMLINK} of=${SDIMG} bs=1 count=442 conv=notrunc
	dd if=${DEPLOY_DIR_IMAGE}/${BL1_SYMLINK} of=${SDIMG} bs=512 skip=1 seek=1 conv=notrunc

	# Burn u-boot
	dd if=${DEPLOY_DIR_IMAGE}/${UBOOT_SYMLINK} of=${SDIMG} bs=512 seek=64 conv=notrunc

	# Burn Partition
	dd if=${SDIMG_ROOTFS} of=${SDIMG} conv=notrunc seek=1 bs=$(expr ${IMAGE_ROOTFS_ALIGNMENT} \* 1024) && sync && sync
}
