SUMMARY = "dreambox video and audio encoder source elements for Gstreamer 1.0"
SECTION = "multimedia"
LICENSE = "CC-BY-NC-SA-3.0 | DreamProperty"
LIC_FILES_CHKSUM = "file://COPYING;md5=1f6263f0928e695084f3f0055be80eb3"
DEPENDS = "glib-2.0-native gstreamer1.0 gstreamer1.0-plugins-base"
SRCREV = "2ca36504b47bde9ba7414fb361f669ad344ec151"

inherit autotools opendreambox-git pkgconfig

do_install_append() {
        rm -f ${D}${libdir}/gstreamer-1.0/*.a
        rm -f ${D}${libdir}/gstreamer-1.0/*.la
}

FILES_${PN} = "${libdir}/gstreamer-1.0/*.so"
FILES_${PN}-dbg += "${libdir}/gstreamer-1.0/.debug"
