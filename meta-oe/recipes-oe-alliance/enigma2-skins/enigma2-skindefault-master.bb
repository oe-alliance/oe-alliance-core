SUMMARY = "A default skin for Enigma2."
MAINTAINER = "OE-Alliance"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README.md;md5=ec650e1109116ae1feccebec8d4e01e1"
inherit allarch

inherit autotools gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

PACKAGES = "enigma2-skindefault"
PROVIDES = "enigma2-skindefault"

do_populate_sysroot[noexec] = "1"

SRC_URI = "git://github.com/oe-alliance/oe-alliance-e2-skindefault.git;protocol=https"
SRC_URI_openspa = "git://github.com/openspa/oe-alliance-e2-skindefault.git;protocol=https"
SRC_URI_openvix = "git://github.com/oe-alliance/oe-alliance-e2-skindefault.git;protocol=https;branch=scalable"

S = "${WORKDIR}/git"

DESCRIPTION_font-roboto-enigma = "Roboto enigma font"

PACKAGES =+ " font-roboto-enigma"
PROVIDES =+ " font-roboto-enigma"
FILES_font-roboto-enigma = "${datadir}/fonts"
FILES_enigma2-skindefault = "${datadir}/enigma2"

RDEPENDS_enigma2-skindefault = "font-roboto-enigma"

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    "

