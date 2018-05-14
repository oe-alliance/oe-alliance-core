SUMMARY = "Imports xmltv files into the EPG cache of enigma2"
MAINTAINER = "oe-alliance"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../etc/epgimport/readme.txt;startline=1;endline=4;md5=c162054328d930d453543efef81be1d8"

inherit gitpkgv pythonnative gettext

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r36"

SRC_URI = "git://github.com/oe-alliance/XMLTV-Import.git;protocol=git"

S = "${WORKDIR}/git/src"

inherit distutils-openplugins

DEPENDS = "python"
RDEPENDS_${PN} = "python-compression python-shell python-lzma python-pkgutil"
RRECOMMENDS_${PN} = "epgimport-rytec"

PACKAGES = "${PN}-src ${PN}-dbg ${PN}"

PLUGIN = "EPGImport"

FILES_${PN} = "${libdir}/enigma2/python/Plugins/Extensions/${PLUGIN}/ /etc"
FILES_${PN}-dbg = "${libdir}/enigma2/python/Plugins/Extensions/${PLUGIN}/.debug /usr/src/debug"
FILES_${PN}-src = "${libdir}/enigma2/python/Plugins/Extensions/${PLUGIN}/*.py"

# skip this!
install_egg_info() {
}

do_install_prepend (){
	install -d ${D}/${sysconfdir}/epgimport
	install -m 755 ${S}/../etc/epgimport/readme.txt ${D}${sysconfdir}/epgimport/readme.txt
}