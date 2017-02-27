require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

SRC_URI[md5sum] = "edbdc798f23ae0f8045c82f6fa22c536"
SRC_URI[sha256sum] = "6f220258f3ce9f11d0e53860ccc2d9fe746117056212099876dbe5ee1de80af3"

do_install_append_arm() {
	cp ${S}/include/linux/usb/video.h ${D}${includedir}/linux/usb/video.h
}

SRC_URI += " \
        file://dvb_frontend-Multistream-support-3.1.patch \
"
