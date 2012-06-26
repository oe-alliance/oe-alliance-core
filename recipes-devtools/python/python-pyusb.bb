DESCRIPTION = "libusb Python Bindings"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "BSD"
DEPENDS = "virtual/libusb0"
SRCNAME = "pyusb"

require conf/license/license-gplv2.inc

inherit gitpkgv
#SRCREV = ${AUTOREV}
SRCREV = "c95f49487bb54812f98ebaf3b6eebaec06103651"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r2"

SRC_URI = "git://github.com/walac/pyusb.git;protocol=git"
S = "${WORKDIR}/git"

inherit distutils
