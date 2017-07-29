SUMMARY = "Enigma2 plugin to play Blu-ray discs"
DESCRIPTION = "Small plugin to to play Blu-ray discs"
HOMEPAGE = "https://github.com/Taapat/enigma2-plugin-blurayplayer"
SECTION = "multimedia"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit gitpkgv pythonnative
SRCREV = "${AUTOREV}"
PV = "1+git${SRCPV}"
PKGV = "1+git${GITPKGV}"
BRANCH = "master"

SRC_URI = "git://github.com/Taapat/enigma2-plugin-blurayplayer.git;branch=${BRANCH}"

S = "${WORKDIR}/git"

inherit distutils-openplugins

DEPENDS = "python libbluray libudfread"

FILES_${PN}-dbg += "/usr/lib/enigma2/python/Plugins/Extensions/BlurayPlayer/.debug"
