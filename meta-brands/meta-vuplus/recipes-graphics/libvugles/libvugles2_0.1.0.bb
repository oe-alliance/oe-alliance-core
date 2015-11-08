SUMMARY = "shared library for E2 animation"
SECTION = "libs"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "${SRCDATE}.${SRCDATE_PR}"

require libvugles2-${TARGET_ARCH}.inc

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

COMPATIBLE_MACHINE = "^(vusolose|vusolo2|vuduo2|vusolo4k)$"
