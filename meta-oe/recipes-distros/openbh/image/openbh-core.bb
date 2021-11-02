SUMMARY = "OpenBH Core"
MAINTAINER = "OpenBH"

require conf/license/license-gplv2.inc

DEPENDS = "enigma2 python-process libcrypto-compat-0.9.7 gettext-native"
RDEPENDS_enigma2-plugin-obh-core = "ofgwrite python-process libcrypto-compat-0.9.7 python-compression zip procps python-beautifulsoup4"

RCONFLICTS_enigma2-plugin-obh-core = "settings-autorestore"
RREPLACES_enigma2-plugin-obh-core = "settings-autorestore"

PROVIDES += "openbh-core"
RPROVIDES_enigma2-plugin-obh-core += "openbh-core"

inherit autotools-brokensep gitpkgv pythonnative
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
PR = "r5"

SRC_URI="git://github.com/BlackHole/obh-core.git;protocol=https"

S = "${WORKDIR}/git"

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    "

PACKAGES =+ "enigma2-plugin-obh-core"
PACKAGES =+ "enigma2-plugin-obh-core-src"
PACKAGES =+ "enigma2-plugin-obh-core-po"
CONFFILES_enigma2-plugin-obh-core += "${sysconfdir}/exports"
FILES_enigma2-plugin-obh-core = "/etc ${libdir}"
FILES_enigma2-plugin-obh-core-dbg = "${libdir}/enigma2/python/Plugins/SystemPlugins/OBH/.debug/"
FILES_enigma2-plugin-obh-core-src = "${libdir}/enigma2/python/Plugins/SystemPlugins/OBH/*.py"
FILES_enigma2-plugin-obh-core-po = "${libdir}/enigma2/python/Plugins/SystemPlugins/OBH/locale/*.po"
FILES_enigma2-plugin-obh-core-doc = "/usr/share/enigma2/README*"