DESCRIPTION = "Fetch channels from remote bouquets and make them available locally"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "python3-shell"
RREPLACES:${PN} = "enigma2-plugin-extensions-remotestreamconvert"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/RemoteChannelStreamConverter.git;protocol=https;branch=main"
