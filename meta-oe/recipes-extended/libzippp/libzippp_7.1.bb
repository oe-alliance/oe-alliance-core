DESCRIPTION = "libzippp is a simple basic C++ wrapper around the libzip library."
HOMEPAGE = "https://github.com/ctabin/libzippp"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=23e77e9e1aeb13d22279edbfc101c62d"

DEPENDS = "zlib libzip"

EXTRA_OECMAKE = "-DBUILD_SHARED_LIBS=ON" 

inherit cmake pkgconfig

SRC_URI = "git://github.com/ctabin/libzippp.git;protocol=https;branch=master"

SRCREV = "8992d6002b6ae941ad1a61d5bc7a512277907be3"

S = "${UNPACKDIR}/libzippp-7.1"

do_install:append() {
	rm -r ${D}${datadir}
}

FILES:${PN} = "${libdir}/libzippp.so"
FILES:${PN}-dev = "${includedir}/*"
