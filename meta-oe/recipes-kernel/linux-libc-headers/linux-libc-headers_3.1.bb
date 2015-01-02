require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "4"

SRC_URI[md5sum] = "8d43453f8159b2332ad410b19d86a931"
SRC_URI[sha256sum] = "2573d2378c754b0c602b57586e9311e5b38c5d1e6c137f02873833633a4b9359"

do_install_append_arm() {
	cp ${S}/include/linux/usb/video.h ${D}${includedir}/linux/usb/video.h
}
