require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI[md5sum] = "3e58f5ca4b7e6533cfc6d35896c2fbf4"
SRC_URI[sha256sum] = "ba5d53862c2f3762322f8cc7760f4cd0117e86aed78991c62ac4f589bc22189c"



# sh4 boxes require some headers from the kernel modules (for the framebuffer and ioctls) which are not shipped by the kernel headers. In order to avoid adding explicit sh4-conditional dependancies to the driver package in several packages (just for a couple of generic headers) we add them here. In this way, we also avoid unnecessary rebuilds of several stuff when drivers are updated.

SRC_URI_append_sh4 = "\
    file://linux-3.4.100_stm_0309.3.patch \
    file://bz85008_over_3.4.100-49.patch \
    file://kernel_header_85508_v4l2-dv-timings.patch \
    file://dvb_frontend-Multistream-support-3.4.patch \
    file://stmfb.h \
    file://stm_ioctls.h \
    file://bpamem.h \
    file://st-coprocessor.h \
    file://extra_ipv6_headers.patch \
"

do_install_append_sh4() {
    install -d ${D}/${includedir}/linux/dvb
    install -m 644 ${WORKDIR}/stm_ioctls.h ${D}/${includedir}/linux/dvb
    install -m 644 ${WORKDIR}/stmfb.h ${D}/${includedir}/linux
    install -m 644 ${WORKDIR}/bpamem.h ${D}/${includedir}
    install -m 644 ${S}/include/linux/usb/video.h ${D}${includedir}/linux/usb/video.h
}
