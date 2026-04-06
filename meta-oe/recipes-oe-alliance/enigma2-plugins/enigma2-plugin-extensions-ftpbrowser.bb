DESCRIPTION = "FTP/SFTP-Browser-Plugin for Enigma2"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "python3-paramiko enigma2-plugin-systemplugins-toolkit"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/FTPBrowser.git;protocol=https;branch=main"
