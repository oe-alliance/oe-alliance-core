inherit image_types

IMAGE_TYPEDEP_fastboot = "ext4"

do_image_fastboot[depends] = "e2fsprogs-native:do_populate_sysroot android-tools-native:do_populate_sysroot"

IMAGE_CMD_fastboot () {
	ext2simg -v ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext4 ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.fastboot
}