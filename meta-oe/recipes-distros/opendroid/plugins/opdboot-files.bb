SUMMARY = "OPDboot files"
LICENSE = "GPL2"
require conf/license/license-gplv2.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

PR = "r2"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/opendroid-Team/OPD-boot-files.git"

FILES_${PN} = "/usr /mipsel /arm"

INHIBIT_PACKAGE_STRIP = "1"

ALLOW_EMPTY_${PN} = "1"

S="${WORKDIR}/git/files"

do_install() {
	install -d ${D}/usr
	mkdir -p ${D}/usr/softcams
}

do_install_append_mipsel() {
	mkdir -p ${D}/${libdir}/enigma2/python/Plugins/Extensions/OPDBoot/bin/
	cp -a ${S}/mipsel/fbclear_mipsel ${D}/${libdir}/enigma2/python/Plugins/Extensions/OPDBoot/bin/fbclear
	cp -a ${S}/mipsel/nfidump_mipsel ${D}/${libdir}/enigma2/python/Plugins/Extensions/OPDBoot/bin/nfidump
	
	mkdir -p ${D}/${libdir}/enigma2/python/Plugins/Extensions/OPDBoot/ubi_reader/ubifs/
	cp -a ${S}/mipsel/lzo_mipsel.so ${D}/${libdir}/enigma2/python/Plugins/Extensions/OPDBoot/ubi_reader/ubifs/lzo.so
}

do_install_append_arm() {
	mkdir -p ${D}/${libdir}/enigma2/python/Plugins/Extensions/OPDBoot/bin/
	cp -a ${S}/arm/fbclear_arm ${D}/${libdir}/enigma2/python/Plugins/Extensions/OPDBoot/bin/fbclear
}
