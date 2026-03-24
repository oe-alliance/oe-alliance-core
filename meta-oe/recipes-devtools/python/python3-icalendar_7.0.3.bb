SUMMARY = "The icalendar package is a parser/generator of iCalendar files for use with Python."
HOMEPAGE = "http://icalendar.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=c1811377eb97cf603a2fc49ed1b0d37f"

DEPENDS = "python3-pytz python3-dateutil python3-hatch-vcs-native"
RDEPENDS:${PN} = "python3-pytz python3-dateutil python3-zoneinfo"

SRC_URI[md5sum] = "4abb97cfdc0768e794e49d05c690cfdf"
SRC_URI[sha256sum] = "95027ece087ab87184d765f03761f25875821f74cdd18d3b57e9c868216d8fde"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
