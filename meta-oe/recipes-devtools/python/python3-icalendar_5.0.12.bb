SUMMARY = "The icalendar package is a parser/generator of iCalendar files for use with Python."
HOMEPAGE = "http://icalendar.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=1b2957cd26c589d0defcb357be630e80"

DEPENDS = "${PYTHON_PN}-pytz ${PYTHON_PN}-dateutil"
RDEPENDS:${PN} = "${PYTHON_PN}-pytz ${PYTHON_PN}-dateutil"

SRC_URI[md5sum] = "778fec235b764a288ff5d755323a6169"
SRC_URI[sha256sum] = "73f9be68477722c98320621400943705dcfdbbc6c2b565253f72d3f87e514db8"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
