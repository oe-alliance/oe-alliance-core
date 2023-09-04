DESCRIPTION = "Force Power LNB/ToneBurst for FBC DVB-S tuners(Lunix3-4K/Galaxy 4K/GigaBlue UE & Quad 4K)"
HOMEPAGE = "https://github.com/Dima73/ForceFbcLNBpower"

LICENSE = "PD"
LIC_FILES_CHKSUM = "file://README.md;md5=1271ae75e9727c17bcf6cdfdd2ad7fd8"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/Dima73/ForceFbcLNBpower.git;branch=master;protocol=https"

require conf/python/python3-compileall.inc

S = "${WORKDIR}/git"

inherit gitpkgv setuptools3-openplugins

PV = "1+git"
PKGV = "1+git${GITPKGV}"
