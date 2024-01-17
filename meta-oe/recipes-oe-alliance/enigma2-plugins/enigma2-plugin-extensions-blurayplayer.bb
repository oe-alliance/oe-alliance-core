SUMMARY = "Enigma2 plugin to play Blu-ray discs"
DESCRIPTION = "Small plugin to to play Blu-ray discs"
HOMEPAGE = "https://github.com/Taapat/enigma2-plugin-blurayplayer"
SECTION = "multimedia"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263"
require conf/python/python3-compileall.inc

inherit gitpkgv ${PYTHON_PN}native
SRCREV = "${AUTOREV}"
PV = "1+git"
PKGV = "1+git${GITPKGV}"
BRANCH = "master"
PR = "r1"

SRC_URI = "git://github.com/Taapat/enigma2-plugin-blurayplayer.git;protocol=https;branch=${BRANCH}"

S = "${WORKDIR}/git"

inherit setuptools3-openplugins

DEPENDS += "${PYTHON_PN}  libbluray libudfread"
RDEPENDS:${PN} = "libbluray"

FILES:${PN}-dbg += "/usr/lib/enigma2/python/Plugins/Extensions/BlurayPlayer/.debug"
