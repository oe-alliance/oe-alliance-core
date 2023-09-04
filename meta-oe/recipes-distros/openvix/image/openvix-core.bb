SUMMARY = "OpenViX Core"
MAINTAINER = "OpenViX"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

DEPENDS = "enigma2 ${PYTHON_PN}-process libcrypto-compat-0.9.7 gettext-native"
RDEPENDS:enigma2-plugin-vix-core = "ffmpeg ofgwrite ${PYTHON_PN}-process libcrypto-compat-0.9.7 ${PYTHON_PN}-compression zip procps bzip2"

RCONFLICTS:enigma2-plugin-vix-core = "settings-autorestore"
RREPLACES:enigma2-plugin-vix-core = "settings-autorestore"

PROVIDES += "openvix-core"
RPROVIDES:enigma2-plugin-vix-core += "openvix-core"

inherit autotools-brokensep gitpkgv ${PYTHON_PN}native
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
PR = "r8"

SRC_URI="git://github.com/OpenViX/vix-core.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    "

PACKAGES =+ "enigma2-plugin-vix-core"
PACKAGES =+ "enigma2-plugin-vix-core-po"

CONFFILES:enigma2-plugin-vix-core += "${sysconfdir}/exports"
FILES:enigma2-plugin-vix-core = "/etc ${libdir}"
FILES:enigma2-plugin-vix-core-src = "${libdir}/enigma2/python/Plugins/SystemPlugins/ViX/*.py"
FILES:enigma2-plugin-vix-core-dbg = "${libdir}/enigma2/python/Plugins/SystemPlugins/ViX/*.debug"
FILES:enigma2-plugin-vix-core-po = "${libdir}/enigma2/python/Plugins/SystemPlugins/ViX/locale/*.po"
FILES:enigma2-plugin-vix-core-doc = "/usr/share/enigma2/README*"


