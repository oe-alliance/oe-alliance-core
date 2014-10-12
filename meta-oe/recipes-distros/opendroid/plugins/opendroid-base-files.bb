DESCRIPTION = "OpenDroid base files"
LICENSE = "GPL2"

require conf/license/license-gplv2.inc

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/opendroid-Team/opendroid-base"

FILES_${PN} = "/media /usr"

INHIBIT_PACKAGE_STRIP = "1"
 
ALLOW_EMPTY_${PN} = "1"

PR = "r11"

S="${WORKDIR}/git/files"

do_install() {
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

	mkdir -p ${D}/usr/lib
	cd ${D}/usr/lib
	ln -s libbz2.so.0.0.0 libbz2.so.1.0 || true


}
