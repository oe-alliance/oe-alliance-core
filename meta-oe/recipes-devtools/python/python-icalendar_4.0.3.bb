SUMMARY = "The icalendar package is a parser/generator of iCalendar files for use with Python."
HOMEPAGE = "http://icalendar.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=1b2957cd26c589d0defcb357be630e80"

DEPENDS = "python-pytz python-dateutil"
RDEPENDS_${PN} = "python-pytz python-dateutil"

SRC_URI = "https://files.pythonhosted.org/packages/5d/92/647cd84120b8d9c2ac9a03ccff21bb44c267c993b88881a32c1837d250bd/icalendar-${PV}.tar.gz"

SRC_URI[md5sum] = "8a30e6129917db2eda13fb38fc56fe8d"
SRC_URI[sha256sum] = "07c2447a1d44cbb27c90b8c6a5c98e890cc1853c6223e2a52195cddec26c6356"

S = "${WORKDIR}/icalendar-${PV}"

inherit setuptools

include python-package-split.inc
