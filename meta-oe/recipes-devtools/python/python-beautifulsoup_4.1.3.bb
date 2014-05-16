SUMMARY = "Beautiful Soup is a Python library designed for quick turnaround projects like screen-scraping."
HOMEPAGE = "http://www.crummy.com/software/BeautifulSoup/"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING.txt;md5=83e365dc17176bd72ba7d08ca0555efa"

PR = "r3"

SRC_URI = "http://www.crummy.com/software/BeautifulSoup/bs4/download/4.1/beautifulsoup4-${PV}.tar.gz"

SRC_URI[md5sum] = "5aece3c0b8a080658155958111fa2fa9"
SRC_URI[sha256sum] = "f1e9b7b48e93efc044c79fa0ac5805094ab6f42f8946ec4abf840753e0dea91f"

S = "${WORKDIR}/beautifulsoup4-${PV}"

inherit distutils

include python-package-split.inc
