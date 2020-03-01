SUMMARY = "RapidXml is an attempt to create the fastest XML parser possible, written in modern C++."
HOMEPAGE = "http://rapidxml.sourceforge.net/"
SECTION = "libs"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://license.txt;md5=d63ab70ba21ca0544b03284958324301"

SRC_URI = "git://github.com/hydranix/rapidxml.git"

SRCREV = "b79d25bddd83941e6f95c30e3d184e959b83417f"

PV = "1.13+git${SRCPV}"

S = "${WORKDIR}/git"

do_install(){
    install -d ${D}/${includedir}/rapidxml
    install -m 0644 ${S}/*.hpp ${D}/${includedir}/rapidxml/
}
