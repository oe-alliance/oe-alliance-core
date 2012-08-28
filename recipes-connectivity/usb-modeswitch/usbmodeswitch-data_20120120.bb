DESCRIPTION = "tool to switch multidevice usb modes"
require conf/license/license-gplv2.inc

LICENSE = "GPL"

PV="20120120"

SRC_URI[md5sum] = "76b96980ce19ccc4c3f9c15b70ea1f08"
SRC_URI[sha256sum] = "d0c90a512dcc98f9425e7464eb453238cef39a91c3c29caa9ade402127e5def2"

SRC_URI +=" http://code-ini.com/software/tools/usb-modeswitch-data-${PV}.tar.gz \
	    file://usb-modeswitch-data_20120215.patch \
	    "

S = "${WORKDIR}/usb-modeswitch-data-${PV}"

do_compile() {
}

do_install() {
	install -d ${D}/usr/share/usb_modeswitch
	#install -d ${D}/etc/usb_modeswitch.d
	#install -d ${D}/lib/udev/rules.d
	#install -m 0755 ${S}/40-usb_modeswitch.rules ${D}/lib/udev/rules.d/
	#install -m 0755 ${S}/usb_modeswitch.d/* ${D}/usr/share/usb_modeswitch/
	DESTDIR=${D} make install
	install -m 0755 ${S}/usb_modeswitch.d/* ${D}/usr/share/usb_modeswitch/
}

PACKAGES =+ "usbmodeswitch-data"
FILES_${PN} += "/etc /lib /usr"
