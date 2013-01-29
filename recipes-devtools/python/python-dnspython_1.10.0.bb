DESCRIPTION = "dnspython is a DNS toolkit for Python."
HOMEPAGE = "http://www.dnspython.org/"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=397eddfcb4bc7e2ece2fc79724a7cca2"

SRC_URI = "http://www.dnspython.org/kits/${PV}/dnspython-${PV}.tar.gz"

SRC_URI[md5sum] = "b4f60852fd7ba64fc7c3a1fa239eba33"
SRC_URI[sha256sum] = "4dc21450ec6ac94dd105b4e5f39a75b404ad45a30869ff73acb6fd4d9974f857"

S = "${WORKDIR}/dnspython-${PV}"

inherit distutils
