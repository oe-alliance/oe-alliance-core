SUMMARY = "create Dreambox NAND boot images"
SECTION = "console/utils"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://src/buildimage.c;endline=17;md5=ea8d8cabec86117939480a328ac3a34b"
PR = "r4"

inherit gitpkgv

VERSION := "${PV}"
PV = "${VERSION}+git${SRCPV}"
PKGV = "${VERSION}+git${GITPKGV}"

SRCREV = "95d4dd192e659f297e782fd71edbee038f9dc443"

SRC_URI = "git://github.com/oe-mirrors/buildimage.git;protocol=https;branch=master \
    file://0001-add-e-option-for-warn-only.patch \
"

S = "${WORKDIR}/git"

inherit autotools

OPENDREAMBOX_PROJECT = "buildimage"
