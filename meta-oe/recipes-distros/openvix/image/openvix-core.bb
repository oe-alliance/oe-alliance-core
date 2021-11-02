SUMMARY = "OpenViX Core"
MAINTAINER = "OpenViX"

require conf/license/license-gplv2.inc

DEPENDS = "enigma2 python-process libcrypto-compat-0.9.7 gettext-native python-future python-six "
RDEPENDS_enigma2-plugin-vix-core = "ofgwrite python-process libcrypto-compat-0.9.7 python-compression zip procps python-beautifulsoup4 bzip2 python-future python-six"

RCONFLICTS_enigma2-plugin-vix-core = "settings-autorestore"
RREPLACES_enigma2-plugin-vix-core = "settings-autorestore"

PROVIDES += "openvix-core"
RPROVIDES_enigma2-plugin-vix-core += "openvix-core"

inherit autotools-brokensep gitpkgv pythonnative
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
PR = "r3"

SRC_URI="git://github.com/OpenViX/vix-core.git;protocol=https"

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
FILES_enigma2-plugin-vix-core = "/etc ${libdir}"
FILES_enigma2-plugin-vix-core-dbg = "${libdir}/enigma2/python/Plugins/SystemPlugins/ViX/.debug/"
FILES_enigma2-plugin-vix-core-src = "${libdir}/enigma2/python/Plugins/SystemPlugins/ViX/*.py"
FILES_enigma2-plugin-vix-core-po = "${libdir}/enigma2/python/Plugins/SystemPlugins/ViX/locale/*.po"
FILES_enigma2-plugin-vix-core-doc = "/usr/share/enigma2/README*"

do_install_append() {
    if [ -f ${DEPLOY_DIR_IMAGE}/burn.bat ]; then
        install -m 755 ${DEPLOY_DIR_IMAGE}/burn.bat ${D}${libdir}/enigma2/python/Plugins/SystemPlugins/ViX/burn.bat
    fi
}
