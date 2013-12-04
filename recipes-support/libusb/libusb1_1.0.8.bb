DESCRIPTION = "Userspace library to access USB (version 1.0)"
HOMEPAGE = "http://libusb.sf.net"
BUGTRACKER = "http://www.libusb.org/report"
SECTION = "libs"
PACKAGE_ARCH := "${MACHINE_ARCH}"

LICENSE = "LGPLv2.1+"
require conf/license/license-gplv2.inc

PR = "r7"

SRC_URI = "${SOURCEFORGE_MIRROR}/libusb/libusb-${PV}.tar.bz2"
SRC_URI_append_dm800 = " file://libusb-1.0.so.0.0.0"

SRC_URI[md5sum] = "37d34e6eaa69a4b645a19ff4ca63ceef"
SRC_URI[sha256sum] = "21d0d3a5710f7f4211c595102c6b9eccb42435a17a4f5bd2c3f4166ab1badba9"

S = "${WORKDIR}/libusb-${PV}"

inherit autotools pkgconfig

EXTRA_OECONF = "--libdir=${base_libdir}"

do_install_append() {
	install -d ${D}${libdir}
	if [ ! ${D}${libdir} -ef ${D}${base_libdir} ]; then
		mv ${D}${base_libdir}/pkgconfig ${D}${libdir}
	fi
}

do_install_append_dm800() {
	cp -rp ${WORKDIR}/libusb-1.0.so.0.0.0 ${D}/lib/
}

FILES_${PN} += "${base_libdir}/*.so.*"

FILES_${PN}-dev += "${base_libdir}/*.so ${base_libdir}/*.la"
