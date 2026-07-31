DESCRIPTION = "plugin to connect to internet via any modems"
HOMEPAGE = "https://github.com/Dima73/enigma2-plugin-extensions-xmodem"
LICENSE = "LicenseRef-PD"
LIC_FILES_CHKSUM = "file://README;md5=00f286ed22b8ad579d0715884c7639a9"

SRC_URI = "git://github.com/oe-mirrors/enigma2-plugin-extensions-xmodem.git;protocol=https;branch=master"

require conf/python/python3-compileall.inc

DEPENDS += "python3-future-native"

SRCREV = "${AUTOREV}"
inherit gitpkgv python3native
PV = "1+git"
PKGV = "1+git${GITPKGV}"

inherit setuptools3-openplugins

RDEPENDS:${PN} = " \
	iptables \
	usb-modeswitch \
	usb-modeswitch-data \
	picocom \
	ppp \
	"
