SUMMARY = "OPDboot files"
LICENSE = "GPL2"
require conf/license/license-gplv2.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r4"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/opendroid-Team/OPD-boot-files.git;protocol=https"

FILES_${PN} += "${libdir}/*"

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

do_install_osmio4k() {
	mkdir -p ${D}/${libdir}/enigma2/python/Plugins/Extensions/OPDBoot/bin/
	cp -a ${S}/aarch64/fbclear_aarch64 ${D}/${libdir}/enigma2/python/Plugins/Extensions/OPDBoot/bin/fbclear
}

do_package_qa() {
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
