SUMMARY = "Enigma2 Device Manager"
MAINTAINER = "SystemPlugins"
SECTION = "base"
PRIORITY = "required"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r2"

SRC_URI[md5sum] = "ae81ab4e3917cec8f0429da513385402"
SRC_URI[sha256sum] = "baa3ae2b49d4498c2e618471d49fff7a16ea18c53a4066455c7c9fcdf20e67ad"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-extensions-DeviceManager_1_0_mips32el.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "${libdir} "

do_install() {
	install -d ${D}${libdir}
	cp -rp ${S}/usr/lib/* ${D}${libdir}/
}
