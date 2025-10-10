SUMMARY = "PCSC tools"
HOMEPAGE = "https://pcsc-tools.apdu.fr/"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENCE;md5=94d55d512a9ba36caa9b7df079bae19f"

DEPENDS = "pcsc-lite autoconf-archive"
RDEPENDS:${PN} = "pcsc-lite"

inherit gittag 

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/LudovicRousseau/pcsc-tools.git;protocol=https;branch=master"

inherit autotools gettext pkgconfig

FILES:${PN} += "${datadir}"
