DESCRIPTION = "urlgrabber is a pure python package that drastically simplifies the fetching of files."
HOMEPAGE = "http://urlgrabber.baseurl.org"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=68ad62c64cc6c620126241fd429e68fe"

DEPENDS = "python-pycurl"

SRC_URI[md5sum] = "9fcf77af476a69da2718e827ea628c6f"
SRC_URI[sha256sum] = "53691185e3d462bb0fa8db853a205ee79cdd4089687cddd22cabb8b3d4280142"

SRC_URI = "http://urlgrabber.baseurl.org/download/urlgrabber-${PV}.tar.gz \
        file://urlgrabber-reset.patch"

S = "${WORKDIR}/urlgrabber-${PV}"

inherit distutils

include python-package-split.inc
