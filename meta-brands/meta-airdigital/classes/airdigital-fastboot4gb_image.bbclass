inherit image_types

IMAGE_TYPEDEP_fastboot4gb = "ext4"

do_image_fastboot4gb[depends] = " \
	android-tools-native:do_populate_sysroot \
	"

IMAGE_CMD_fastboot4gb () {
	ext2simg -zv ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext4 ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.fastboot4gb.gz
}