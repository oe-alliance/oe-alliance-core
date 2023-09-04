SUMMARY = "InfoBarTunerState Extentions"
MAINTAINER = "Betonme"
SECTION = "extra"
PRIORITY = "optional"
DEPENDS = "${PYTHON_PN}-six-native"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit autotools gitpkgv ${PYTHON_PN}native
SRCREV = "${AUTOREV}"
PV = "1.1.+git"
PKGV = "1.1.+git${GITPKGV}"
PR = "r0"

SRC_URI="git://github.com/betonme/e2openplugin-InfoBarTunerState.git;branch=python3;protocol=https"

S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-po"
FILES:${PN} = "/etc /usr/lib"
FILES:${PN}-po = "/usr/lib/enigma2/python/Plugins/Extensions/InfoBarTunerState/locale/*.po"

EXTRA_OECONF = "\
    --with-libsdl=no --with-boxtype=${MACHINE} --with-po \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

pkg_postrm:${PN}() {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/InfoBarTunerState/
echo "Plugin removed! You should restart enigma2 now!"
exit 0
}