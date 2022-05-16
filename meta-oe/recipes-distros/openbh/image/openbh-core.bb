SUMMARY = "OpenBh Core"
MAINTAINER = "OpenBh"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

DEPENDS = "enigma2 ${PYTHON_PN}-process libcrypto-compat-0.9.7 gettext-native"
RDEPENDS:enigma2-plugin-obh-core = "ofgwrite ${PYTHON_PN}-process libcrypto-compat-0.9.7 ${PYTHON_PN}-compression zip procps ${PYTHON_PN}-beautifulsoup4 bzip2"

RCONFLICTS:enigma2-plugin-obh-core = "settings-autorestore"
RREPLACES:enigma2-plugin-obh-core = "settings-autorestore"

PROVIDES += "openbh-core"
RPROVIDES:enigma2-plugin-obh-core += "openbh-core"

inherit autotools-brokensep gitpkgv ${PYTHON_PN}native
SRCREV = "87bec5a67215d1a233fc32c1ec725250211cc094"
PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
PR = "r9"

SRC_URI="git://github.com/BlackHole/obh-core.git;protocol=https"

S = "${WORKDIR}/git"

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    "

PACKAGES =+ "enigma2-plugin-obh-core"
PACKAGES =+ "enigma2-plugin-obh-core-po"

CONFFILES:enigma2-plugin-obh-core += "${sysconfdir}/exports"
FILES:enigma2-plugin-obh-core = "/etc ${libdir}"
FILES:enigma2-plugin-obh-core-src = "${libdir}/enigma2/python/Plugins/SystemPlugins/OBH/.py/"
FILES:enigma2-plugin-obh-core-dbg = "${libdir}/enigma2/python/Plugins/SystemPlugins/OBH/.debug/"
FILES:enigma2-plugin-obh-core-po = "${libdir}/enigma2/python/Plugins/SystemPlugins/OBH/locale/*.po"
FILES:enigma2-plugin-obh-core-doc = "/usr/share/enigma2/README*"