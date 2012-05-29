MODULE = "RemoteChannelStreamConverter"
DESCRIPTION = "Fetch channels from remote bouquets and make them available locally"

DEPENDS_${PN} = "enigma2"
RDEPENDS_${PN} = "python-shell"

SRC_URI = "git://github.com/OpenViX/e2openplugin-${MODULE}.git;protocol=git"

S = "${WORKDIR}/git"

inherit gitpkgv

require assume-gplv2.inc

SRCREV = "${AUTOREV}"
PV = "2.0+git${SRCPV}-${DISTRO_TYPE}"
PKGV = "2.0+git${GITPKGV}-${DISTRO_TYPE}"
PR = "r2"

inherit autotools

EXTRA_OECONF = "\
	--with-po \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
"

PACKAGES =+ "${PN}-src"
PACKAGES =+ "${PN}-po"
FILES_${PN} = "/etc /usr/lib"
FILES_${PN}-dbg = "/usr/lib/enigma2/python/Plugins/Extensions/RemoteChannelStreamConverter/.debug/"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/RemoteChannelStreamConverter/*.py"
FILES_${PN}-po = "${datadir}/enigma2/po/*.po"

# remove unused .pyc files
do_install_append() {
	find ${D}/usr/lib/enigma2/python/Plugins/Extensions/RemoteChannelStreamConverter/ -name '*.pyc' -exec rm {} \;
}

do_install_po() {
	install -d ${D}${datadir}/enigma2/po/
	LANGS="ar bg ca cs da de el en en_GB es et fa fi fr fy he hr hu is it lt lv nl no pl pt ru sv sk sl sr th tr uk"
	for lang in ${LANGS}; do
		install -m 0755 ${S}/po/$lang.po ${D}${datadir}/enigma2/po/RemoteChannelStreamConverter-$lang.po
	done
}

addtask install_po before do_package after do_install
