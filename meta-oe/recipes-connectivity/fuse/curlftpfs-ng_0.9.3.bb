SUMMARY = "This is a filesystem client based on the FTP File Transfer Protocol using FUSE."
AUTHOR = "Robson Braga Araujo - <brag@users.sf.net>"
HOMEPAGE = "https://ikn.org.uk/tool/curlftpfs-ng/"
SECTION = "console/network"
PRIORITY = "optional"
LICENSE = "GPL-2.0-only"
DEPENDS = "glib-2.0 fuse curl"
RDEPENDS:${PN} += " libcurl "

require conf/license/license-gplv2.inc

inherit gittag autotools pkgconfig
SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"
PR = "r0"

S = "${WORKDIR}/git"

SRC_URI = "git://github.com/ikn/curlftpfs-ng.git;branch=master;protocol=https"
