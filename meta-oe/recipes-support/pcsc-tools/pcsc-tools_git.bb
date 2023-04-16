SUMMARY = "PCSC tools"
HOMEPAGE = "http://ludovic.rousseau.free.fr/softwares/pcsc-tools/"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENCE;md5=94d55d512a9ba36caa9b7df079bae19f"

DEPENDS = "pcsc-lite autoconf-archive"
RDEPENDS:${PN} = "pcsc-lite"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.6.2+git${SRCPV}"
PKGV = "1.6.2+git${GITPKGV}"

SRC_URI = "git://github.com/LudovicRousseau/pcsc-tools.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit autotools gettext pkgconfig

FILES:${PN} += "${datadir}"
