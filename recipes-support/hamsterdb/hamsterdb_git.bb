DESCRIPTION = "HamsterDB"
LICENSE = "GPLv3"

LIC_FILES_CHKSUM = "file://COPYING.GPL3;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "boost"

SRC_URI = "git://github.com/cruppstahl/hamsterdb.git;protocol=git"

S = "${WORKDIR}/git"

inherit gitpkgv autotools

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"
