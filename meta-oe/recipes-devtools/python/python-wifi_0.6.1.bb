SUMMARY = "Provides access to Linux Wireless Extensions"
HOMEPAGE = "http://pythonwifi.tuxfamily.org"
SECTION = "devel/python"
LICENSE = "LGPLv2+"
LICENSE_${PN}-examples = "LGPLv2+"
LIC_FILES_CHKSUM = "file://README;beginline=56;endline=57;md5=68b329da9893e34099c7d8ad5cb9c940 \
                    file://examples/iwconfig.py;beginline=1;endline=20;md5=d34accb82b4f998eccccfd4f4eb56f32 \
                    file://pythonwifi/iwlibs.py;beginline=1;endline=22;md5=aa48daae5660dfd2bb23b2cafe2789e1"

RDEPENDS_${PN} = "python-ctypes python-datetime"

PR = "r1"

SRC_URI = "https://files.pythonhosted.org/packages/bc/ab/c49f97516f78c2b0cacb4f45873abc4ca9872942a9c4c19ded8052c8edda/python-wifi-0.6.1.tar.bz2"
SRC_URI[md5sum] = "033227169230286a63b9c059e7465c77"
SRC_URI[sha256sum] = "7bff6afbc03b1cb13f987e5cf3b597b8821a1b86e5b34182406d98657f1b2c91"

SRC_URI_append_aarch64 = " file://python-wifi-0.6.1-64bit.patch"

inherit setuptools

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

include python-package-split.inc
