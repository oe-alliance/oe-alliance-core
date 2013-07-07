DESCRIPTION = "The icalendar package is a parser/generator of iCalendar files for use with Python."
HOMEPAGE = "http://icalendar.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=8ff644958c37784425f321ace93fb329"

PR = "r16"

DEPENDS_${PN} = "python-pytz python-dateutil"
RDEPENDS_${PN} = "python-pytz python-dateutil"

SRC_URI = "http://pypi.python.org/packages/source/i/icalendar/icalendar-${PV}.tar.gz"

SRC_URI[md5sum] = "f787789421382a09b3047069dbc6d265"
SRC_URI[sha256sum] = "2fc1094896b9dcdfc87b5c07ec4a4dd3b65b70c1650e91884eff96ccb9e89ea9"

S = "${WORKDIR}/icalendar-${PV}"

inherit distutils

FILES_${PN}-tests = " \
  ${libdir}/${PYTHON_DIR}/site-packages/*/tests \
  ${libdir}/${PYTHON_DIR}/site-packages/*/*/tests \
"

PACKAGES =+ "${PN}-tests"
