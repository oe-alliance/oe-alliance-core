DESCRIPTION = "urlgrabber is a pure python package that drastically simplifies the fetching of files."

HOMEPAGE = "http://urlgrabber.baseurl.org/"
SECTION = "devel/python"
PRIORITY = "optional"

require conf/license/license-gplv2.inc

SRC_URI[md5sum] = "00c8359bf71062d0946bacea521f80b4"
SRC_URI[sha256sum] = "4437076c8708e5754ea04540e46c7f4f233734ee3590bb8a96389264fb0650d0"

PR = "r1"

SRC_URI = "http://urlgrabber.baseurl.org/download/urlgrabber-${PV}.tar.gz \
           file://urlgrabber-HEAD.patch;patch=1 \
           file://urlgrabber-reset.patch;patch=1"
S = "${WORKDIR}/urlgrabber-${PV}"

DEPENDS = "python-pycurl"

inherit distutils
