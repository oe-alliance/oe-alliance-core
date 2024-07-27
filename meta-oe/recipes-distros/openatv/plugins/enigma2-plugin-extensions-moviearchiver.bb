SUMMARY = "Archive or Backup your Movielist automatically. MovieArchiver by svox"
MAINTAINER = "svox"
SECTION = "extra"
PRIORITY = "optional"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv gettext
SRCREV = "${AUTOREV}"
PV = "0.3.+git"
PKGV = "0.3.+git${GITPKGV}"
PR = "r0"

SRC_URI="git://github.com/oe-mirrors/enigma2-plugin-extensions-moviearchiver.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-po"
FILES:${PN} = "${libdir}"
FILES:${PN}-src = "${libdir}/enigma2/python/Plugins/Extensions/MovieArchiver/*.py"
FILES:${PN}-po = "${libdir}/enigma2/python/Plugins/Extensions/MovieArchiver/locale/*/*/*.po"

inherit autotools-brokensep

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"
