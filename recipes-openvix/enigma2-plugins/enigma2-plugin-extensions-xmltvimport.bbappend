FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "1"

SRC_URI = "git://git.assembla.com/openvix.6.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools

DEPENDS += " xmltvimport-library"
RDEPENDS_${PN} += " xmltvimport-library"
RRECOMMENDS_${PN} = "enigma2-plugin-vix-xmltvimport"
PACKAGES = "${PN}-dbg ${PN}"
PACKAGES =+ "${PN}-src"
PACKAGES =+ "${PN}-po"

PLUGIN = "EPGImport"

FILES_${PN} = "/usr/bin /usr/lib/enigma2/python /etc"
FILES_${PN}-dbg = "/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}/.debug"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}/*.py"
FILES_${PN}-po = "${datadir}/enigma2/po/*.po"

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
