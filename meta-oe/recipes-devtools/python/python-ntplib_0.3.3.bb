SUMMARY = "Python NTP library"
DESCRIPTION = "This module offers a simple interface to query NTP servers from Python"
HOMEPAGE = "https://pypi.org/project/ntplib"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://ntplib.py;beginline=1;endline=23;md5=afa07338a9595257e94c205c3e72224d"

RDEPENDS_${PN} = "python-core"

SRC_URI = "https://files.pythonhosted.org/packages/29/8b/85a86e01c510665b0790d3a9fd4532ad98aba9e185a676113a0ae3879350/ntplib-${PV}.tar.gz"

SRC_URI[md5sum] = "c7cc8e9b09f40c84819859d70b7784ca"
SRC_URI[sha256sum] = "c4621b64d50be9461d9bd9a71ba0b4af06fbbf818bbd483752d95c1a4e273ede"

S = "${WORKDIR}/ntplib-${PV}"

inherit allarch distutils

include python-package-split.inc
