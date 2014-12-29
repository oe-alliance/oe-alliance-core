SUMMARY = "shared library for E2 animation"
SECTION = "libs"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCDATE = "20141210"
SRCDATE_PR = "r3"
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

SRC_URI[md5sum] = "a9f3552dcbf185153f010022b6e62f3b"
SRC_URI[sha256sum] = "be31586f0b54fc671d4c26c89f75b9e0b5754ed3de1f47d37f7560ed3febffff"
