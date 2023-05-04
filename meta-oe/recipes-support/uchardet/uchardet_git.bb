DESCRIPTION = "uchardet is a C language binding of the original C++ \
implementation of the universal charset detection library by Mozilla."
HOMEPAGE = "https://github.com/BYVoid/uchardet"
LICENSE = "MPLv1.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=6ecda54f6f525388d71d6b3cd92f7474"

SRC_URI = "git://gitlab.freedesktop.org/uchardet/uchardet.git;protocol=https;branch=master"

S = "${WORKDIR}/git/"

inherit pkgconfig cmake gitpkgv

PV = "0.0.8+git${SRCPV}"
PKGV = "0.0.8+git${GITPKGV}"

SRCREV = "${AUTOREV}"
