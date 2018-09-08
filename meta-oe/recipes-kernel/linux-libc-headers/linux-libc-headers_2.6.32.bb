require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI[md5sum] = "f9c782399f1609c67bb78405a8d6e021"
SRC_URI[sha256sum] = "723106c298c5ba8733bb51ecc106b05e332a18490edc5f61cc6e57dbb36dcc46"

SRC_URI += "\
    file://linuxdvb.patch \
    file://ppp.patch \
    file://types.patch \
    file://taskstats.patch \
"

# sh4 boxes require some headers from the kernel modules (for the framebuffer and ioctls) which are not shipped by the kernel headers. In order to avoid adding explicit sh4-conditional dependancies to the driver package in several packages (just for a couple of generic headers) we add them here. In this way, we also avoid unnecessary rebuilds of several stuff when drivers are updated.

SRC_URI_append_sh4 = "\
    file://stmfb.h \
    file://stm_ioctls.h \
    file://bpamem.h \
"

do_install_append_sh4() {
    install -d ${D}/${includedir}/linux/dvb
    install -m 644 ${WORKDIR}/stm_ioctls.h ${D}/${includedir}/linux/dvb
    install -m 644 ${WORKDIR}/stmfb.h ${D}/${includedir}/linux
    install -m 644 ${WORKDIR}/bpamem.h ${D}/${includedir}
    install -m 644 ${S}/include/linux/usb/video.h ${D}${includedir}/linux/usb/video.h
}

