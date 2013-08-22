DESCRIPTION = "Binary Property List (plist) files provide a faster and smaller\
	       serialization format for property lists on OS X. This is a\
	       library for generating binary plists which can be read by OS X,\
	       iOS, or other clients."
SECTION = "devel/python"
PRIORITY = "optional"
HOMEPAGE = "http://github.com/wooster/biplist"
SRCNAME = "biplist"
DEPENDS = "python"
LICENSE = "BSD"
PR = "r1"

require conf/license/license-gplv2.inc

SRC_URI = "https://github.com/downloads/wooster/biplist/${SRCNAME}-${PV}.tar.gz"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools

SRC_URI[md5sum] = "870f2e0d16a48440b37dadedca06552b"
SRC_URI[sha256sum] = "d3097952e344b5d440c7588162faadf02474cd522527acd9e0a0a4923ddbff45"

