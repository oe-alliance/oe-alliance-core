DESCRIPTION = "An enigma2 client for Emby servers"
MAINTAINER = "DimitarCC"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"
HOMEPAGE = "https://github.com/DimitarCC/e2-emby-client"

RDEPENDS:${PN} = "python3-pillow python3-requests python3-yt-dlp"

require conf/python/python3-compileall.inc

inherit gitpkgv allarch gettext setuptools3-openplugins

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/DimitarCC/e2-emby-client.git;protocol=https;branch=main"

