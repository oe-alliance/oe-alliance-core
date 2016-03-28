SUMMARY = "EGAMI base files"
LICENSE = "GPL2"
require conf/license/license-gplv2.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

DEPENDS = "zip unrar egamiemud"
RDEPENDS_${PN} = "zip unrar egamiemud"

PR = "r3"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/a4tech/egami-base-files.git"

FILES_${PN} = "/bin /etc /scripts /media /usr /mipsel /arm"

INHIBIT_PACKAGE_STRIP = "1"

ALLOW_EMPTY_${PN} = "1"

S="${WORKDIR}/git/files"

do_install() {
	install -d ${D}/etc
	cp -a ${S}/etc/* ${D}/etc/

	install -d ${D}/media
	mkdir -p ${D}/media/card
	mkdir -p ${D}/media/cf
	mkdir -p ${D}/media/hdd
	mkdir -p ${D}/media/net
	mkdir -p ${D}/media/upnp
	mkdir -p ${D}/media/usb
	mkdir -p ${D}/media/usb1
	mkdir -p ${D}/media/usb2
	mkdir -p ${D}/media/usb3

	install -d ${D}/usr
	cp -a ${S}/usr/* ${D}/usr/
	mkdir -p ${D}/usr/uninstall
}

do_install_append_mipsel() {
	mkdir -p ${D}/usr/lib/enigma2/python/Plugins/Extensions/EGAMIBoot/bin/
	cp -a ${S}/mipsel/fbclear_mipsel ${D}/usr/lib/enigma2/python/Plugins/Extensions/EGAMIBoot/bin/fbclear
	cp -a ${S}/mipsel/nfidump_mipsel ${D}/usr/lib/enigma2/python/Plugins/Extensions/EGAMIBoot/bin/nfidump
	
	mkdir -p ${D}/usr/lib/enigma2/python/Plugins/Extensions/EGAMIBoot/ubi_reader/ubifs/
	cp -a ${S}/mipsel/lzo_mipsel.so ${D}/usr/lib/enigma2/python/Plugins/Extensions/EGAMIBoot/ubi_reader/ubifs/lzo.so
}

do_install_append_arm() {
	mkdir -p ${D}/usr/lib/enigma2/python/Plugins/Extensions/EGAMIBoot/bin/
	cp -a ${S}/arm/fbclear_arm ${D}/usr/lib/enigma2/python/Plugins/Extensions/EGAMIBoot/bin/fbclear
}