AUTHOR = "Grog68"
MAINTAINER = "TwolDE2"
DESCRIPTION = "Movie Organiser plugin by Grog68"
SUMMARY = "Movie Organiser plugin by Grog68"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gittag allarch setuptools3-openplugins

S = "${UNPACKDIR}/${BP}/src"

DEPENDS += " gettext-native"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

SRC_URI = "git://github.com/TwolDE2/MovieOrganisor.git;protocol=https;branch=main"
