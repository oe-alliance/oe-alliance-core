inherit image_types

IMAGE_TYPEDEP_fastboot = "ext4"

IMAGE_DEPENDS_fastboot = " \
	e2fsprogs-native \
	android-tools-native \
	"

IMAGE_CMD_fastboot () {
	ext2simg -v ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext4 ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.fastboot
}