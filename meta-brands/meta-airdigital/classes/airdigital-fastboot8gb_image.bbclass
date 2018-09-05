inherit image_types

IMAGE_TYPEDEP_fastboot8gb = "ext4"

do_image_fastboot8gb[depends] = " \
	android-tools-native:do_populate_sysroot \
	"

IMAGE_CMD_fastboot8gb () {
	ext2simg -zv ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext4 ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.fastboot8gb.gz
}