MAINTAINER = "RAED Developer"
SUMMARY = "Ncam - Open Source Softcam"
DESCRIPTION = "Combining the benefits of latest trunk and modern interface and emu support"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

require conf/license/license-gplv2.inc
inherit cmake gitpkgv

SRCREV = "${AUTOREV}"
SVNVER = "Unofficial"
PV = "${SVNVER}+git"
PKGV = "${PV}"
PR = "r1"

DEPENDS = "libusb openssl libdvbcsa curl"
RDEPENDS:${PN} += "libdvbcsa libusb1"

SRC_URI = "git://github.com/fairbird/NCam.git;protocol=https;branch=master \
        file://init_ncam \
        file://ncam.conf.example \
        file://ncam.server.example \
        file://ncam.user.example \
        "

S = "${WORKDIR}/git"
UNPACKDIR = "${WORKDIR}/sources-unpack"

FILES:${PN} = "/usr/bin/ /usr/script/ /var/volatile/log/ncam/ /etc/tuxbox/config/ncam/*"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${TUNE_PKGARCH}"

LDFLAGS:prepend = "-ldvbcsa "
EXTRA_OECONF = "LIBDVBCSA=yes "

EXTRA_OECMAKE += "\
	-DNCAM_SYSTEM_NAME=Tuxbox \
	-DWEBIF=1 \
	-DWITH_STAPI=0 \
	-DWITH_LIBCUR=1 \
	-DWITH_LIBCRYPTO=1 \
	-DHAVE_LIBUSB=1 \
	-DSTATIC_LIBUSB=1 \
	-DWITH_SSL=1 \
	-DIPV6SUPPORT=1 \
	-DCLOCKFIX=0 \
	-DHAVE_PCSC=1 \
	-DCARDREADER_SMARGO=1 \
	-DCARDREADER_PCSC=1 \
	-DCW_CYCLE_CHECK=1 \
	-DCS_CACHEEX=1 \
	-DMODULE_SCAM=1 \
	-DMODULE_STREAMRELAY=1 \
	"

do_install() {
    install -d ${D}/usr/script
    install -m 0755 ${UNPACKDIR}/init_ncam ${D}/usr/script/Ncam_cam.sh
    install -d ${D}/${bindir}
    install -m 0755 ${WORKDIR}/build/ncam ${D}/usr/bin/ncam
    install -d ${D}/${sysconfdir}/tuxbox/config/ncam
    install -m 0644 ${UNPACKDIR}/ncam.conf.example ${D}/etc/tuxbox/config/ncam
    install -m 0644 ${UNPACKDIR}/ncam.server.example ${D}/etc/tuxbox/config/ncam
    install -m 0644 ${UNPACKDIR}/ncam.user.example ${D}/etc/tuxbox/config/ncam
    install -d ${D}/var/volatile/log/ncam/
}

pkg_prerm:${PN}() {
#!/bin/sh

exit 0
}

pkg_postrm:${PN}() {
#!/bin/sh

rm -rf /etc/tuxbox/config/ncam/ncam.conf.example > /dev/null 2>&1
rm -rf /etc/tuxbox/config/ncam/ncam.server.example > /dev/null 2>&1
rm -rf /etc/tuxbox/config/ncam/ncam.user.example > /dev/null 2>&1
rm -rf /usr/bin/ncam > /dev/null 2>&1
rm -rf /usr/script/Ncam_cam.sh > /dev/null 2>&1

exit 0
}

INSANE_SKIP:${PN} += "already-stripped build-deps"

