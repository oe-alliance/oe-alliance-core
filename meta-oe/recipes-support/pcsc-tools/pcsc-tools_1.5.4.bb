SUMMARY = "PCSC tools"
HOMEPAGE = "http://ludovic.rousseau.free.fr/softwares/pcsc-tools/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENCE;md5=94d55d512a9ba36caa9b7df079bae19f"

DEPENDS = "pcsc-lite"
RDEPENDS_${PN} = "pcsc-lite"

inherit autotools pkgconfig

SRCREV = "e6efd02002decabe1dfa956a5257109190fc6ae8"
SRC_URI = "git://github.com/LudovicRousseau/pcsc-tools.git"

S = "${WORKDIR}/git"

FILES_${PN} += "/usr/share"
