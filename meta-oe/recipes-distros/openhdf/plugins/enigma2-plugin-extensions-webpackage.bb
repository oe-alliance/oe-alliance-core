SUMMARY = "Enigma2 Web Manager Package"
MAINTAINER = "SystemPlugins & Extensions"
SECTION = "base"
PRIORITY = "required"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r4"

SRC_URI[md5sum] = "791cde8bfd986b915f88d1838f02ad42"
SRC_URI[sha256sum] = "a4aa3dff185858e4e558ee93522688da20b18620e7c72eca99b0ddf169374fc5"

RDEPENDS_${PN} = "python-difflib openssl python-pyopenssl"

SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-web_package_mips32el.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "${libdir} /etc/*"

do_install() {
	install -d ${D}${libdir}
	install -d ${D}/etc
	cp -rp ${S}/usr/lib/* ${D}${libdir}/
	cp -rp ${S}/etc/* ${S}/etc/
}
