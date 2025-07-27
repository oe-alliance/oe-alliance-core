SUMMARY = "The icalendar package is a parser/generator of iCalendar files for use with Python."
HOMEPAGE = "http://icalendar.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=1b2957cd26c589d0defcb357be630e80"

DEPENDS = "python3-pytz python3-dateutil python3-hatch-vcs-native"
RDEPENDS:${PN} = "python3-pytz python3-dateutil python3-zoneinfo"

SRC_URI[md5sum] = "85dcf09c4da0e95ea152ede497d1694f"
SRC_URI[sha256sum] = "a697ce7b678072941e519f2745704fc29d78ef92a2dc53d9108ba6a04aeba466"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
