SUMMARY = "Provides access to Linux Wireless Extensions"
HOMEPAGE = "http://pythonwifi.wikispot.org/"
SECTION = "devel/python"
LICENSE = "LGPLv2+ & GPLv2+"
LICENSE_${PN}-examples = "GPLv2+"
LIC_FILES_CHKSUM = "file://README;md5=54307cbab01c3aad9adf7605132bcf31"

RDEPENDS_${PN} = "${PYTHON_PN}-ctypes ${PYTHON_PN}-datetime"

SRC_URI = "git://github.com/oe-mirrors/python3-wifi.git"

SRCREV ?= "fcfd5a75d6ec03369d0906c9dd953d6d5a0aecaf"

SRC_URI_append_aarch64 += " file://python-wifi-0.6.1-64bit.patch"

inherit setuptools3

S = "${WORKDIR}/git"

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
