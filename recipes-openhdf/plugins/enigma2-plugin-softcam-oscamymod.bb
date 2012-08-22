DESCRIPTION = "Oscam ymod"
MAINTAINER = "ymod"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="18"
PR = "t33"

SRC_URI[md5sum] = "a0f97ac3d4dbd2db7d8683d96d8e7fca"
SRC_URI[sha256sum] = "771555eabf8fc99674fd0a2e2c3e0c5fee1670c32d921e84cb8e0af816469cb3"


SRC_URI = "http://addons.hdfreaks.cc/feeds/*git/enigma2-plugins-softcam-OscamEmu.tar.gz"

S = "${WORKDIR}"

FILES_${PN} = "/usr/* \
		/etc/*"

do_install() {
cp -rp ${S}/usr ${S}/etc ${D}/
}
