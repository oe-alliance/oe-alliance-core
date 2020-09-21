SUMMARY = "shared library for E2 animation"
SECTION = "libs"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "${SRCDATE}.${SRCDATE_PR}"

require libvugles2-${TARGET_ARCH}.inc

INHIBIT_PACKAGE_STRIP = "1"
S = "${WORKDIR}/${PN}-${PV}"

inherit pkgconfig

do_install() {
	install -d ${D}${includedir}
	cp -r ${S}/include/* ${D}${includedir}/
	install -d ${D}${libdir}
	cp -r ${S}/lib/*.so ${D}${libdir}/
	install -d ${D}${libdir}/pkgconfig
	cp -r ${S}/lib/pkgconfig/*.pc ${D}${libdir}/pkgconfig/
}

PACKAGES = "${PN}"
FILES_${PN} = "/usr/include /usr/lib"

COMPATIBLE_MACHINE = "^(vusolose|vusolo2|vuduo2|vusolo4k|vuuno4k|vuuno4kse|vuultimo4k|vuzero4k|vuduo4k|vuduo4kse)$"

INSANE_SKIP_${PN} += "already-stripped dev-so file-rdeps"
