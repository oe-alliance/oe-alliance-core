SUMMARY = "Remote support through a shared terminal in the web browser"
DESCRIPTION = "Shares a terminal of the receiver with a supporter through sshx. Every participant has to be approved on the TV or in OpenWebif."
HOMEPAGE = "https://github.com/oe-alliance-plugins/RemoteSupport"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://../LICENSE.txt;md5=e62637ea8a114355b985fd86c9ffbd6e"
require conf/python/python3-compileall.inc

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/RemoteSupport.git;protocol=https;branch=main"

RDEPENDS:${PN} = "sshx python3-cbor2 python3-cryptography python3-pyte python3-websocket-client"

RPROVIDES:${PN} += "remotesupport"

do_install:append() {
	chmod 0755 ${D}${bindir}/remotesupport
}
