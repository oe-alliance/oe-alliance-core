DESCRIPTION = "gstreamer dvbmediasink plugin"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "MIT | LGPLv2.1"
LIC_FILES_CHKSUM = "file://src/gstdvbaudiosink.c;beginline=1;endline=45;md5=62569cb59c6b5603c31dfa99d88ebaad \
                    file://src/gstdvbvideosink.c;beginline=1;endline=45;md5=43bc87dd28b3b3d5a0bae89d41e5cf1c"

DEPENDS = "gstreamer gst-plugins-base libdca fulan-dvb-modules"

SRCREV = "6d79026a12591038663721c14adb76f8f5c3cc42"

SRC_URI = "git://github.com/Duckbox-Developers/apps.git;protocol=git"

S = "${WORKDIR}/git/libs/gst-plugins-dvbmediasink"

PV = "0.10.0"
PKGV = "0.10.0"
PR = "r2"

inherit autotools pkgconfig

do_configure_prepend() {
    touch ${S}/NEWS
}

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so*"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"

PACKAGE_ARCH = "${MACHINE_ARCH}"

EXTRA_OECONF = "${DVBMEDIASINK_CONFIG}"
