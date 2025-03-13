SUMMARY = "The icalendar package is a parser/generator of iCalendar files for use with Python."
HOMEPAGE = "http://icalendar.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=1b2957cd26c589d0defcb357be630e80"

DEPENDS = "python3-pytz python3-dateutil python3-hatch-vcs-native"
RDEPENDS:${PN} = "python3-pytz python3-dateutil python3-zoneinfo"

SRC_URI[md5sum] = "228d80d99d00ad0353c4e5b5b28e3000"
SRC_URI[sha256sum] = "2c44355a8f006de5ae73fa3f022a1cbe2a0de6b1607ce8879739eb887c4f3471"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
