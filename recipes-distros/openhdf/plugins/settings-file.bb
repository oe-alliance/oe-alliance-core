DESCRIPTION = "Enigma2 settings files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "HDF Team"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r7"

do_compile() {
}

do_install() {
	mkdir -p ${D}/etc/enigma2
	echo "config.skin.primary_skin=Nobile/extras/skin.xml
" >> ${D}/etc/enigma2/settings
}
