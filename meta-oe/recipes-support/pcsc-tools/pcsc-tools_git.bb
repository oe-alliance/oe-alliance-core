SUMMARY = "PCSC tools"
HOMEPAGE = "http://ludovic.rousseau.free.fr/softwares/pcsc-tools/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENCE;md5=94d55d512a9ba36caa9b7df079bae19f"

DEPENDS = "pcsc-lite"
RDEPENDS_${PN} = "pcsc-lite"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.5.4+git${SRCPV}"
PKGV = "1.5.4+git${GITPKGV}"

SRC_URI = "git://github.com/LudovicRousseau/pcsc-tools.git;protocol=https"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

FILES_${PN} += "${datadir}"
