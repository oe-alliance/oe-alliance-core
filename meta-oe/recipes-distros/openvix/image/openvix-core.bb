SUMMARY = "OpenViX Core"
MAINTAINER = "OpenViX"

require conf/license/license-gplv2.inc

DEPENDS = "enigma2 python-process libcrypto-compat-0.9.7"
RDEPENDS_enigma2-plugin-vix-core = "ofgwrite python-process libcrypto-compat-0.9.7 python-compression zip procps python-beautifulsoup bzip2"

RCONFLICTS_enigma2-plugin-vix-core = "settings-autorestore"
RREPLACES_enigma2-plugin-vix-core = "settings-autorestore"

PROVIDES += "openvix-core"
RPROVIDES_enigma2-plugin-vix-core += "openvix-core"

inherit autotools-brokensep gitpkgv pythonnative
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
PR = "r1"

SRC_URI="git://github.com/OpenViX/vix-core.git;protocol=git"

S = "${WORKDIR}/git"

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    "

PACKAGES =+ "enigma2-plugin-vix-core"
PACKAGES =+ "enigma2-plugin-vix-core-src"
PACKAGES =+ "enigma2-plugin-vix-core-po"
CONFFILES_enigma2-plugin-vix-core += "${sysconfdir}/exports"
FILES_enigma2-plugin-vix-core = "/etc /usr/lib"
FILES_enigma2-plugin-vix-core-dbg = "/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/.debug/"
FILES_enigma2-plugin-vix-core-src = "/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/*.py"
FILES_enigma2-plugin-vix-core-po = "/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/locale/*.po"
FILES_enigma2-plugin-vix-core-doc = "/usr/share/enigma2/README*"

do_install_append() {
    if [ -f ${DEPLOY_DIR_IMAGE}/burn.bat ]; then
        install -m 755 ${DEPLOY_DIR_IMAGE}/burn.bat ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/burn.bat
    fi
}
