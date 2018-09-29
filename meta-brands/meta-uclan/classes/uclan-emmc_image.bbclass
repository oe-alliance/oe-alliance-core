inherit image_types

IMAGE_TYPEDEP_uclanemmc = "ext4"

do_image_uclanemmc[depends] = " \
    parted-native:do_populate_sysroot \
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    virtual/kernel:do_populate_sysroot \
    uclan-buildimage-native:do_populate_sysroot \
    "

IMAGE_CMD_uclanemmc () {

}
