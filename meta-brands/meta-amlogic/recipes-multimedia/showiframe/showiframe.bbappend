FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append:gtxcombo = " file://showiframe-meson.c"
DEPENDS:append:gtxcombo = " ffmpeg"
LICENSE:gtxcombo = "MIT"
LIC_FILES_CHKSUM:gtxcombo = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
do_compile[depends] += "${@bb.utils.contains('MACHINE', 'gtxcombo', 'virtual/kernel:do_shared_workdir', '', d)}"

do_compile:gtxcombo() {
    ${CC} ${CPPFLAGS} ${CFLAGS} -I${STAGING_KERNEL_DIR}/common_drivers/include/uapi/amlogic \
        ${S}/showiframe-meson.c -o showiframe ${LDFLAGS} -lavformat -lavcodec -lavutil
}
