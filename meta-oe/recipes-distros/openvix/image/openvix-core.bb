SUMMARY = "OpenViX Core"
MAINTAINER = "OpenViX"

require conf/license/license-gplv2.inc

DEPENDS = "enigma2 ${PYTHON_PN}-process libcrypto-compat-0.9.7 gettext-native"
RDEPENDS_enigma2-plugin-vix-core = "ofgwrite ${PYTHON_PN}-process libcrypto-compat-0.9.7 ${PYTHON_PN}-compression zip procps ${PYTHON_PN}-beautifulsoup4 bzip2"

RCONFLICTS_enigma2-plugin-vix-core = "settings-autorestore"
RREPLACES_enigma2-plugin-vix-core = "settings-autorestore"

PROVIDES += "openvix-core"
RPROVIDES_enigma2-plugin-vix-core += "openvix-core"

inherit autotools-brokensep gitpkgv ${PYTHON_PN}native
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
PR = "r5"

SRC_URI="git://github.com/TwolDE/vix-core.git;protocol=git;branch=master"

S = "${WORKDIR}/git"

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    "

PACKAGES =+ "enigma2-plugin-vix-core"

CONFFILES_enigma2-plugin-vix-core += "${sysconfdir}/exports"
FILES_enigma2-plugin-vix-core = "/etc ${libdir}"
FILES_enigma2-plugin-vix-core-dbg = "${libdir}/enigma2/python/Plugins/SystemPlugins/ViX/.debug/"
FILES_enigma2-plugin-vix-core-src = "${libdir}/enigma2/python/Plugins/SystemPlugins/ViX/*.py"
FILES_enigma2-plugin-vix-core-po = "${libdir}/enigma2/python/Plugins/SystemPlugins/ViX/locale/*.po"
FILES_enigma2-plugin-vix-core-doc = "/usr/share/enigma2/README*"


