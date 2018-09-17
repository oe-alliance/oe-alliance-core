inherit image_types

IMAGE_TYPEDEP_hdfastboot8gb = "ext4"

do_image_hdfastboot8gb[depends] = " \
	android-tools-native:do_populate_sysroot \
	"

IMAGE_CMD_hdfastboot8gb () {
	ext2simg -zv ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext4 ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.hdfastboot8gb.gz
}