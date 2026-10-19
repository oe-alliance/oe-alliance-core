DESCRIPTION = "Music player and library for OpenATV 8 with extended tags and cover art"
HOMEPAGE = "https://github.com/oe-alliance-plugins/Jukebox"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://Jukebox/LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "python3-misc python3-mutagen python3-sqlite3 python3-twisted-web"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/Jukebox.git;protocol=https;branch=main"
