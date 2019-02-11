inherit image_types

IMAGE_TYPEDEP_beyonwizemmc = "ext4"

do_image_beyonwizemmc[depends] = " \
    parted-native:do_populate_sysroot \
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    virtual/kernel:do_populate_sysroot \
    beyonwiz-buildimage-native:do_populate_sysroot \
    "

IMAGE_CMD_beyonwiznemmc () {

}
