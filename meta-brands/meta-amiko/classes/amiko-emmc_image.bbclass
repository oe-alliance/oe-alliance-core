inherit image_types

IMAGE_TYPEDEP_amikoemmc = "ext4"

do_image_amikoemmc[depends] = " \
    parted-native:do_populate_sysroot \
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    virtual/kernel:do_populate_sysroot \
    amiko-buildimage-native:do_populate_sysroot \
    "

IMAGE_CMD_amikoemmc () {

}
