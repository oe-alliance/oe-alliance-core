DESCRIPTION = "OE-A Enigma2 Remotes for OE-A 2.0"
MAINTAINER = "oe-alliance team"
PACKAGE_ARCH = "${MACHINEBUILD}"

DEPENDS = "python"

require conf/license/license-gplv2.inc

inherit gitpkgv autotools pythonnative

SRCREV = "${AUTOREV}"
PV = "0.1+git${SRCPV}"
PKGV = "0.1+git${GITPKGV}"
PR = "r0"

SRC_URI="git://github.com/oe-alliance/branding-module.git;protocol=git"

S = "${WORKDIR}/git"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-boxtype=${MACHINEBUILD} \
    --with-machineoem="${MACHINE_OEM}" \
    --with-machinebrand="${MACHINE_BRAND}" \
    "

FILES_${PN} = "/usr"
