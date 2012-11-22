MODULE = "RemoteChannelStreamConverter"
DESCRIPTION = "Fetch channels from remote bouquets and make them available locally"

DEPENDS = "enigma2 python"
RDEPENDS = "python-shell"

SRC_URI = "git://github.com/oe-alliance/e2openplugin-${MODULE}.git;protocol=git"

S = "${WORKDIR}/git"

inherit gitpkgv

require assume-gplv2.inc

SRCREV = "${AUTOREV}"
PV = "2.0+git${SRCPV}-${DISTRO_TYPE}"
PKGV = "2.0+git${GITPKGV}-${DISTRO_TYPE}"
PR = "r4"

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
FILES_${PN}-po = "/usr/lib/enigma2/python/Plugins/Extensions/RemoteChannelStreamConverter/po/*.po /usr/lib/enigma2/python/Plugins/Extensions/RemoteChannelStreamConverter/po/*.pot"

# remove unused .pyc files
do_install_append() {
	find ${D}/usr/lib/enigma2/python/Plugins/Extensions/RemoteChannelStreamConverter/ -name '*.pyc' -exec rm {} \;
}

do_install_po() {
	install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/RemoteChannelStreamConverter/po
	LANGS="ar bg ca cs da de el en en_GB es et fa fi fr fy he hr hu is it lt lv nl no nb pl pt pt_BR ro ru sv sk sl sr th tr uk"
	for lang in ${LANGS}; do
		install -m 0755 ${S}/po/$lang.po ${D}/usr/lib/enigma2/python/Plugins/Extensions/RemoteChannelStreamConverter/po/$lang.po
	done
	install -m 0755 ${S}/po/rcsc.pot ${D}/usr/lib/enigma2/python/Plugins/Extensions/RemoteChannelStreamConverter/po/rcsc.pot
}

addtask install_po before do_package after do_install
