SUMMARY = "Multiquick Button 4 Ixussone"
MAINTAINER = "SystemPlugins"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r2"

SRC_URI[md5sum] = "bc742ba41c4f8dd4ed4930541be2fe87"
SRC_URI[sha256sum] = "8679b337125b614b4ed460bf7928a42fc731d24207b28fe74c327f747f1377ae"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-update-MultiQuickButton-Ixuss.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "${libdir} /etc/*"

do_install() {
	install -d ${D}${libdir}
	cp -rp ${S}/usr/lib/* ${D}${libdir}/
	cp -rp ${S}/etc ${D}/
}
