SUMMARY = "A default skin for Enigma2."
MAINTAINER = "OE-Alliance"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=ec650e1109116ae1feccebec8d4e01e1"
inherit allarch

inherit autotools gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r3"

PACKAGES = "enigma2-skindefault"
PROVIDES = "enigma2-skindefault"

do_populate_sysroot[noexec] = "1"

SRC_URI = "git://github.com/oe-alliance/oe-alliance-e2-skindefault.git;protocol=https;branch=master"
SRC_URI:openatv = "git://github.com/openatv/oe-alliance-e2-skindefault.git;protocol=https;branch=master"
SRC_URI:openspa = "git://github.com/openspa/oe-alliance-e2-skindefault.git;protocol=https;branch=master"
SRC_URI:openvix = "git://github.com/OpenViX/oe-alliance-e2-skindefault.git;protocol=https;branch=scalable"
SRC_URI:openbh = "git://github.com/BlackHole/oe-alliance-e2-skindefault.git;protocol=https;branch=scalable"

S = "${WORKDIR}/git"

DESCRIPTION:font-roboto-enigma = "Roboto enigma font"

PACKAGES =+ " font-roboto-enigma"
PROVIDES =+ " font-roboto-enigma"
FILES:font-roboto-enigma = "${datadir}/fonts"
FILES:enigma2-skindefault = "${datadir}/enigma2"

RDEPENDS:enigma2-skindefault = "font-roboto-enigma"

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    "

