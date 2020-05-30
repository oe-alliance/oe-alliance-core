SUMMARY = "Provides access to Linux Wireless Extensions"
HOMEPAGE = "http://pythonwifi.wikispot.org/"
SECTION = "devel/python"
LICENSE = "LGPLv2+ & GPLv2+"
LICENSE_${PN}-examples = "GPLv2+"
LIC_FILES_CHKSUM = "file://README;beginline=54;endline=55;md5=31ebd3ff22b6f3c0160a143e0c4a98a3 \
					file://examples/iwconfig.py;beginline=1;endline=20;md5=d34accb82b4f998eccccfd4f4eb56f32 \
					file://${PYTHON_PN}wifi/iwlibs.py;beginline=1;endline=22;md5=aa48daae5660dfd2bb23b2cafe2789e1 \
					"

RDEPENDS_${PN} = "${PYTHON_PN}-ctypes ${PYTHON_PN}-datetime"

SRC_URI = "https://pypi.${PYTHON_PN}.org/packages/bc/ab/c49f97516f78c2b0cacb4f45873abc4ca9872942a9c4c19ded8052c8edda/python-wifi-${PV}.tar.bz2 \
			file://port-to-python3.patch \
"

SRC_URI_append_aarch64 += " file://python-wifi-0.6.1-64bit.patch"

SRC_URI[md5sum] = "033227169230286a63b9c059e7465c77"
SRC_URI[sha256sum] = "7bff6afbc03b1cb13f987e5cf3b597b8821a1b86e5b34182406d98657f1b2c91"

inherit setuptools3

do_install_append() {
		install -d ${D}${docdir}/${PN}
		mv ${D}${datadir}/README ${D}${docdir}/${PN}
		mv ${D}${datadir}/docs/* ${D}${docdir}/${PN}
		rmdir ${D}${datadir}/docs
		install -d ${D}${sbindir}
		mv ${D}${datadir}/examples/* ${D}${sbindir}
		rmdir ${D}${datadir}/examples
}

PACKAGES =+ "${PN}-examples"

FILES_${PN}-examples = "${sbindir}"

include ${PYTHON_PN}-package-split.inc
