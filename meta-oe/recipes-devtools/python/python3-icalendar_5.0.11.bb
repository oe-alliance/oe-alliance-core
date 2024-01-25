SUMMARY = "The icalendar package is a parser/generator of iCalendar files for use with Python."
HOMEPAGE = "http://icalendar.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=1b2957cd26c589d0defcb357be630e80"

DEPENDS = "${PYTHON_PN}-pytz ${PYTHON_PN}-dateutil"
RDEPENDS:${PN} = "${PYTHON_PN}-pytz ${PYTHON_PN}-dateutil"

SRC_URI[md5sum] = "801039f92de296253efafdfb27f85880"
SRC_URI[sha256sum] = "7a298bb864526589d0de81f4b736eeb6ff9e539fefb405f7977aa5c1e201ca00"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
