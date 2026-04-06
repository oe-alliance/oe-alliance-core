DESCRIPTION = "BitrateViewer"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

DEPENDS = "enigma2"

inherit gittag 

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins autotools pkgconfig python3targetconfig

SRC_URI = "git://github.com/oe-alliance-plugins/bitrateviewer.git;protocol=https;branch=main"

CPPFLAGS:append = " -I${STAGING_INCDIR}/python${PYTHON_BASEVERSION}"

FILES:${PN} += "${libdir}/enigma2/python/Plugins/Extensions/BitrateViewer"
