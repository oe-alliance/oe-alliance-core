SUMMARY = "Provides access to Linux Wireless Extensions"
HOMEPAGE = "http://pythonwifi.wikispot.org/"
SECTION = "devel/python"
LICENSE = "LGPLv2+"
LICENSE_${PN}-examples = "GPLv2+"
LIC_FILES_CHKSUM = "file://README;beginline=56;endline=57;md5=31ebd3ff22b6f3c0160a143e0c4a98a3 \
                    file://examples/iwconfig.py;beginline=1;endline=20;md5=60fd41501905b3e20e9065995edfc0cf \
                    file://pythonwifi/iwlibs.py;beginline=1;endline=22;md5=679475d61cc083a24158bb8b473f0c6f"
RDEPENDS_${PN} = "python-ctypes python-datetime"
PR = "r1"

SRC_URI = "http://download.berlios.de/pythonwifi/${P}.tar.bz2"
SRC_URI[md5sum] = "8fe7fd0a4edce1f9bedaff4acb7fd500"
SRC_URI[sha256sum] = "3e3f645d37ab20450f60c785cec5f21b330f28a6c46c7c1b0898305dd7a34b26"

inherit setuptools

do_install_append() {
        install -d ${D}${docdir}/${PN}
        mv ${D}${datadir}/README ${D}${docdir}/${PN}
        mv ${D}${datadir}/INSTALL ${D}${docdir}/${PN}
        mv ${D}${datadir}/docs/* ${D}${docdir}/${PN}
        rmdir ${D}${datadir}/docs
        install -d ${D}${sbindir}
        mv ${D}${datadir}/examples/* ${D}${sbindir}
        rmdir ${D}${datadir}/examples
}

PACKAGES =+ "${PN}-examples"

FILES_${PN}-examples = "${sbindir}"
