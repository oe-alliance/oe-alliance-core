SUMMARY = "shared library for E2 animation"
SECTION = "libs"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE_ARCH}"
require conf/license/license-close.inc

SRCDATE = "20150109"
SRCDATE_PR = "r1"
PR = "${SRCDATE}.${SRCDATE_PR}"

SRC_URI = "http://archive.vuplus.com/download/build_support/vuplus/${PN}-${PV}-${PR}.tar.gz"

INHIBIT_PACKAGE_STRIP = "1"
S = "${WORKDIR}/${PN}-${PV}"

inherit pkgconfig

do_install() {
	install -d ${D}${includedir}
	cp -a ${S}/include/* ${D}${includedir}/
	install -d ${D}${libdir}
	cp -a ${S}/lib/*.so ${D}${libdir}/
	install -d ${D}${libdir}/pkgconfig
	cp -a ${S}/lib/pkgconfig/*.pc ${D}${libdir}/pkgconfig/
}

PACKAGES = "${PN}"
FILES_${PN} = "/usr/include /usr/lib"

SRC_URI[md5sum] = "6aa14c822d9975334d964d385a6b5926"
SRC_URI[sha256sum] = "1e321ca68e14401d4115fb5ac7934c025b0a2b266d1556905f5ef324968921e3"
