SUMMARY = "The icalendar package is a parser/generator of iCalendar files for use with Python."
HOMEPAGE = "http://icalendar.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=1b2957cd26c589d0defcb357be630e80"

DEPENDS = "${PYTHON_PN}-pytz ${PYTHON_PN}-dateutil"
RDEPENDS_${PN} = "${PYTHON_PN}-pytz ${PYTHON_PN}-dateutil"

SRC_URI = "https://files.pythonhosted.org/packages/e4/dd/67c363b99c4384c66bcf94c1abf9b749dd4e809a44bd6db575ec8e22be89/icalendar-4.0.6.tar.gz"
SRC_URI[md5sum] = "d0a6ce988bb9efc34fe765cd9bfe3a8a"
SRC_URI[sha256sum] = "7e6fe7232622abe32d8f54d0936ffcd5a9087198a4c2f1ec1803a7dd9fdd979f"

S = "${WORKDIR}/icalendar-${PV}"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
