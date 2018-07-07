inherit image_types

IMAGE_TYPEDEP_clapemmc = "ext4"

do_image_clapemmc[depends] = " \
    parted-native:do_populate_sysroot \
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    virtual/kernel:do_populate_sysroot \
    clap-buildimage-native:do_populate_sysroot \
    "

IMAGE_CMD_clapemmc () {

}
