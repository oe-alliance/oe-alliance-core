SUMMARY = "create Dreambox NAND boot images"
SECTION = "console/utils"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://src/buildimage.c;endline=17;md5=ea8d8cabec86117939480a328ac3a34b"
PR = "r1"

inherit gitpkgv

VERSION := "${PV}"
PV = "${VERSION}+git${SRCPV}"
PKGV = "${VERSION}+git${GITPKGV}"

SRCREV = "8f00307fdaa5d1ce3d6ffbe92e0f90c219a10500"

SRC_URI = "git://git.opendreambox.org/git/buildimage.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools

BBCLASSEXTEND = "native"

OPENDREAMBOX_PROJECT = "buildimage"
