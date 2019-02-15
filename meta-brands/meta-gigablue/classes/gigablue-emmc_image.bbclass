inherit image_types

IMAGE_TYPEDEP_gigablueemmc = "ext4"

do_image_gigablueemmc[depends] = " \
    parted-native:do_populate_sysroot \
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    virtual/kernel:do_populate_sysroot \
    gigablue-buildimage-native:do_populate_sysroot \
    "

IMAGE_CMD_gigablueemmc () {

}
