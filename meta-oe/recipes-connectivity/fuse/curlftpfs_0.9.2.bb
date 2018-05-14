SUMMARY = "This is a filesystem client based on the FTP File Transfer Protocol using FUSE."
AUTHOR = "Robson Braga Araujo - <brag@users.sf.net>"
HOMEPAGE = "http://curlftpfs.sourceforge.net/"
SECTION = "console/network"
PRIORITY = "optional"
LICENSE = "GPLv2"
DEPENDS = "glib-2.0 fuse curl"
RDEPENDS_${PN} += " libcurl "
PR = "r3"

require conf/license/license-gplv2.inc

SRC_URI = "${SOURCEFORGE_MIRROR}/curlftpfs/${BP}.tar.gz"

S = "${WORKDIR}/${BP}"

inherit autotools pkgconfig

SRC_URI[md5sum] = "b452123f755114cd4461d56c648d9f12"
SRC_URI[sha256sum] = "4eb44739c7078ba0edde177bdd266c4cfb7c621075f47f64c85a06b12b3c6958"
