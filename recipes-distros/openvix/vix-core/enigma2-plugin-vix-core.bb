DESCRIPTION = "Vix Core"
MAINTAINER = "Andy Blackburn"

require conf/license/license-gplv2.inc

DEPENDS = "enigma2 mtd-utils python-process libcrypto-compat"
RDEPENDS = "mtd-utils python-process libcrypto-compat util-linux-blkid python-compression zip procps"

RCONFLICTS_${PN} = "settings-autorestore"
RREPLACES_${PN} = "settings-autorestore"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "2.1+git${GITPKGV}"
PR = "r18"

SRC_URI="git://github.com/OpenViX/vix-core.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools

EXTRA_OECONF = "\
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	--with-po \
	--with-libsdl=no \
"

PACKAGES =+ "${PN}-src"
PACKAGES =+ "${PN}-po"
FILES_${PN} = "/etc /usr/lib"
FILES_${PN}-dbg = "/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/.debug/"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/*.py"
FILES_${PN}-po = "/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/locale/*.po"
FILES_${PN}-doc = "/usr/share/enigma2/README*"

do_install_append() {
	if [ -f ${DEPLOY_DIR_IMAGE}/burn.bat ]; then
		install -m 755 ${DEPLOY_DIR_IMAGE}/burn.bat ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/burn.bat
	fi
}
