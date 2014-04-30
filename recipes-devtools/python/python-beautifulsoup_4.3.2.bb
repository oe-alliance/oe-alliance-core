SUMMARY = "Beautiful Soup is a Python library designed for quick turnaround projects like screen-scraping."
HOMEPAGE = "http://www.crummy.com/software/BeautifulSoup/"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING.txt;md5=83e365dc17176bd72ba7d08ca0555efa"

PR = "r0"

SRC_URI = "http://www.crummy.com/software/BeautifulSoup/bs4/download/4.3/beautifulsoup4-${PV}.tar.gz"

SRC_URI[md5sum] = "b8d157a204d56512a4cc196e53e7d8ee"
SRC_URI[sha256sum] = "a2b29bd048ca2fe54a046b29770964738872a9747003a371344a93eedf7ad58e"

S = "${WORKDIR}/beautifulsoup4-${PV}"

inherit distutils

include python-package-split.inc
