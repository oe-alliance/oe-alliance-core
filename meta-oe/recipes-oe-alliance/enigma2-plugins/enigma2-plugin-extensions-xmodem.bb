DESCRIPTION = "plugin to connect to internet via any modems"
HOMEPAGE = "https://github.com/Dima73/enigma2-plugin-extensions-xmodem"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://README;md5=00f286ed22b8ad579d0715884c7639a9"
SRC_URI = "git://github.com/oe-mirrors/enigma2-plugin-extensions-xmodem.git"

DEPENDS = "${PYTHON_PN}-future-native"

S = "${WORKDIR}/git"

SRCREV = "${AUTOREV}"
inherit gitpkgv ${PYTHON_PN}native
PV = "1+git${SRCPV}"
PKGV = "1+git${GITPKGV}"

inherit ${@bb.utils.contains("PYTHON_PN", "python", "distutils-openplugins", "distutils3-openplugins", d)}

RDEPENDS:${PN} = " \
	iptables \
	usb-modeswitch \
	usb-modeswitch-data \
	picocom \
	ppp \
	"
