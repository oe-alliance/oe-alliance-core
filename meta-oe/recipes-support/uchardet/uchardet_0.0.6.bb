DESCRIPTION = "uchardet is a C language binding of the original C++ \
implementation of the universal charset detection library by Mozilla."
HOMEPAGE = "https://github.com/BYVoid/uchardet"
LICENSE = "MPLv1.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=6ecda54f6f525388d71d6b3cd92f7474"

SRC_URI = "https://www.freedesktop.org/software/uchardet/releases/uchardet-0.0.6.tar.xz"

S = "${WORKDIR}/uchardet-${PV}"

inherit pkgconfig cmake

SRC_URI[md5sum] = "03425c0bbe5faaf399e15e947d3e03c7"
SRC_URI[sha256sum] = "8351328cdfbcb2432e63938721dd781eb8c11ebc56e3a89d0f84576b96002c61"
