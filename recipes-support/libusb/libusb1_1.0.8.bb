DESCRIPTION = "Userspace library to access USB (version 1.0)"
HOMEPAGE = "http://libusb.sf.net"
BUGTRACKER = "http://www.libusb.org/report"
SECTION = "libs"

LICENSE = "LGPLv2.1+"
require conf/license/license-gplv2.inc

PR = "r5"

SRC_URI = "${SOURCEFORGE_MIRROR}/libusb/libusb-${PV}.tar.bz2;name=default"
SRC_URI_ebox5100 = "http://archiv.mixos-support.com/2.6.18-wr-libusb-1.0.8-r01.tar.bz2;name=legacy"
SRC_URI_ebox5000 = "http://archiv.mixos-support.com/2.6.18-wr-libusb-1.0.8-r01.tar.bz2;name=legacy"
SRC_URI_dm800 = "http://archiv.mixos-support.com/2.6.18-wr-libusb-1.0.8-r01.tar.bz2;name=legacy"

SRC_URI[default.md5sum] = "37d34e6eaa69a4b645a19ff4ca63ceef"
SRC_URI[default.sha256sum] = "21d0d3a5710f7f4211c595102c6b9eccb42435a17a4f5bd2c3f4166ab1badba9"
SRC_URI[legacy.md5sum] = "e40fda381d83217748e73493be6586c4"
SRC_URI[legacy.sha256sum] = "2c16b044f52dbed3c970c6d90137583d83065d92652610ef733e35b915399ce6"

S = "${WORKDIR}/libusb-${PV}"

inherit autotools pkgconfig

EXTRA_OECONF = "--libdir=${base_libdir}"

do_install_append() {
	install -d ${D}${libdir}
	if [ ! ${D}${libdir} -ef ${D}${base_libdir} ]; then
		mv ${D}${base_libdir}/pkgconfig ${D}${libdir}
	fi
}

do_install_ebox5100_append() {
	cp -rp ${S}/lib ${D}/
}

do_install_ebox5000_append() {
	cp -rp ${S}/lib ${D}/
}

do_install_dm800_append() {
	cp -rp ${S}/lib ${D}/
}

FILES_${PN} += "${base_libdir}/*.so.*"

FILES_${PN}-dev += "${base_libdir}/*.so ${base_libdir}/*.la"
