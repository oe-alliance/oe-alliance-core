DESCRIPTION="Gmediarender DLNA Renderer"
MAINTAINER = "TitanNit Developer"
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

DEPENDS = " \
	fuse \
	"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "2.0+gitr${SRCPV}"
PR = "r1"
PR = "r2"

SRC_URI="git://github.com/vadmium/rarfs.git;protocol=https"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

#FILES_${PN} = "/usr/bin"
#FILES_${PN} += "/usr/share/gmediarender"
