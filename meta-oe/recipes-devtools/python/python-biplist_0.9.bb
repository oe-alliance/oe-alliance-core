SUMMARY = "Binary Property List (plist) files provide a faster and smaller\
           serialization format for property lists on OS X. This is a\
           library for generating binary plists which can be read by OS X,\
           iOS, or other clients."
SECTION = "devel/python"
PRIORITY = "optional"
HOMEPAGE = "https://pypi.python.org/pypi/biplist"
SRCNAME = "biplist"
DEPENDS = "python"
LICENSE = "BSD"

require conf/license/license-gplv2.inc

SRC_URI = " https://bitbucket.org/wooster/biplist/downloads/${SRCNAME}-${PV}.tar.gz"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools

SRC_URI[md5sum] = "e7c57465356c22a819ddb3a5b20ac94d"
SRC_URI[sha256sum] = "b35bc5602fb01c8f8bd05ff18c55f4e861c382d1bd6fb12ce55fc81e9706d1af"

