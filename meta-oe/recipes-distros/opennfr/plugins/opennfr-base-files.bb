DESCRIPTION = "OpenNFR base files"
LICENSE = "GPL2"

require conf/license/license-gplv2.inc

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCREV = "${AUTOREV}"

SRC_URI = ""

SRC_URI_append_mipsel = "git://github.com/carlo0815/openNFR-base.git" 
SRC_URI_append_sh4 = "git://github.com/carlo0815/openNFR-base.git;protocol=git;branch=sh4" 

FILES_${PN} = "/*"

INHIBIT_PACKAGE_STRIP = "1"
 
ALLOW_EMPTY_${PN} = "1"

PR = "r12"

S="${WORKDIR}/git/files"

do_install() {
}

do_install_append_mipsel() {
	install -d ${D}/media
	mkdir -p ${D}/media/hdd
	mkdir -p ${D}/media/usb

	mkdir -p ${D}/usr/lib
	cd ${D}/usr/lib
	ln -s libbz2.so.0.0.0 libbz2.so.1.0 || true
	install -d ${D}/usr/lib/enigma2/python/Components/Converter
	install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/Infopanel/data
	cp -rp ${S}/usr/lib/enigma2/python/Components/Converter/* ${D}/usr/lib/enigma2/python/Components/Converter
	cp -rp ${S}/usr/lib/enigma2/python/Plugins/Extensions/Infopanel/data/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/Infopanel/data
	mv ${D}/usr/lib/enigma2/python/Components/Converter/bitratecalc.so_mips ${D}/usr/lib/enigma2/python/Components/Converter/bitratecalc.so
}

do_install_append_sh4() {
	install -d ${D}/media
	mkdir -p ${D}/media/hdd
	mkdir -p ${D}/media/usb

	mkdir -p ${D}/usr/lib
	cd ${D}/usr/lib
	ln -s libbz2.so.0.0.0 libbz2.so.1.0 || true
	install -d ${D}/usr/lib/enigma2/python/Components/Converter
	cp -rp ${S}/usr/lib/enigma2/python/Components/Converter/bitratecalc.so_sh4 ${D}/usr/lib/enigma2/python/Components/Converter/bitratecalc.so_sh4
	mv ${D}/usr/lib/enigma2/python/Components/Converter/bitratecalc.so_sh4 ${D}/usr/lib/enigma2/python/Components/Converter/bitratecalc.so
}
