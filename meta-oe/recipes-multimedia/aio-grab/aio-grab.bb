DESCRIPTION="AiO screenshot grabber"
MAINTAINER = "PLi team"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "jpeg libpng zlib"

inherit gitpkgv

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

SRC_URI="git://github.com/oe-alliance/aio-grab.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit autotools pkgconfig
