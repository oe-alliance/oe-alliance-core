SUMMARY = "The icalendar package is a parser/generator of iCalendar files for use with Python."
HOMEPAGE = "http://icalendar.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=1b2957cd26c589d0defcb357be630e80"

DEPENDS = "python-pytz python-dateutil"
RDEPENDS_${PN} = "python-pytz python-dateutil"

SRC_URI = "https://files.pythonhosted.org/packages/db/14/b7c29d8d1ba0e04370c302e815bcb626c725ea9cfe72f7ee337872053558/icalendar-${PV}.tar.gz"
SRC_URI[md5sum] = "cfff9da463e9292d8f6b39bff9b34e4a"
SRC_URI[sha256sum] = "347151cb935f5f2f83d58f6dd8aef8558df69f6c1709f1d2cab2000b36def299"

S = "${WORKDIR}/icalendar-${PV}"

inherit setuptools

include python-package-split.inc
