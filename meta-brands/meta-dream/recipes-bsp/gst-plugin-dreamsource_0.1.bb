SUMMARY = "dreambox video and audio encoder source elements for Gstreamer"
SECTION = "multimedia"
LICENSE = "CLOSED"
DEPENDS = "glib-2.0-native gstreamer1.0 gstreamer1.0-plugins-base"

inherit autotools pkgconfig

SRC_URI = "file://gst-plugin-dreamsource.zip"

S = "${UNPACKDIR}"

RDEPENDS:${PN} = "gstreamer1.0-plugins-bad-videoparsersbad \
                  gstreamer1.0-plugins-bad-codectimestamper \
                  gstreamer1.0-plugins-bad-mpegtsmux \
                  gstreamer1.0-plugins-good-audioparsers"

do_install:append() {
        rm -f ${D}${libdir}/gstreamer-1.0/*.a
        rm -f ${D}${libdir}/gstreamer-1.0/*.la
}

FILES:${PN} = "${libdir}/gstreamer-1.0/libgstdreamsource.so"
FILES:${PN}-dbg += "${libdir}/gstreamer-1.0/.debug"
