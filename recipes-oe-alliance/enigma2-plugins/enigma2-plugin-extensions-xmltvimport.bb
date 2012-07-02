DESCRIPTION = "Imports xmltv files into the EPG cache of enigma2"
MAINTAINER = "oe-alliance"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://etc/epgimport/readme.txt;startline=1;endline=4;md5=c162054328d930d453543efef81be1d8"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r26"

SRC_URI = "git://github.com/oe-alliance/XMTV-Import.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools

DEPENDS = "python xmltvimport-library"
RDEPENDS_${PN} = "python-compression python-shell xmltvimport-library"
RRECOMMENDS_${PN} = "${PN}-rytec"
PACKAGES = "${PN}-dbg ${PN} ${PN}-src ${PN}-po"


PLUGIN = "EPGImport"

FILES_${PN} = "/usr/bin /usr/lib/enigma2/python /etc"
FILES_${PN}-dbg = "/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}/.debug"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}/*.py"
FILES_${PN}-po = "/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}/po/*.po"

EXTRA_OECONF = "\
	--with-po \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	"

do_install_append() {
	# remove unused .pyc files
	find ${D}/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}/ -name '*.pyc' -exec rm {} \;
}

# skip this!
install_egg_info() {
}
