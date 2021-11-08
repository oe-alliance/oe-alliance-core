SUMMARY = "Enigma2 plugin to play Blu-ray discs"
DESCRIPTION = "Small plugin to to play Blu-ray discs"
HOMEPAGE = "https://github.com/Taapat/enigma2-plugin-blurayplayer"
SECTION = "multimedia"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit gitpkgv ${PYTHON_PN}native
SRCREV = "${AUTOREV}"
PV = "1+git${SRCPV}"
PKGV = "1+git${GITPKGV}"
BRANCH = "master"
PR = "r1"

SRC_URI = "git://github.com/Taapat/enigma2-plugin-blurayplayer.git;protocol=https;branch=${BRANCH}"

S = "${WORKDIR}/git"

inherit distutils3-openplugins

DEPENDS = "${PYTHON_PN}  libbluray libudfread"
RDEPENDS:${PN} = "libbluray"

FILES:${PN}-dbg += "/usr/lib/enigma2/python/Plugins/Extensions/BlurayPlayer/.debug"
