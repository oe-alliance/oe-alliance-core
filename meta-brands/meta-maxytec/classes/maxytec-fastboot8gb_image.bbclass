inherit image_types

IMAGE_TYPEDEP_maxytecfastboot8gb = "ext4"
IMAGE_ROOTFS_SIZE = "1048576"

do_image_maxytecfastboot8gb[depends] = " \
	android-tools-native:do_populate_sysroot \
	"

IMAGE_CMD_maxytecfastboot8gb () {
	ext2simg -zv ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext4 ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.fastboot8gb.gz
}