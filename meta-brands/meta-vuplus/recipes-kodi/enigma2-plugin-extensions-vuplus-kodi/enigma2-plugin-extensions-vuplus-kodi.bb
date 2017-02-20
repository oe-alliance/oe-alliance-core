DESCRIPTION = "Enigma2 plugin to launch and play media from Kodi"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PV = "17.0"
PR = "r0"

COMPATIBLE_MACHINE = "^(vuduo2|vusolo2|vusolose|vusolo4k|vuultimo4k|vuuno4k)$"

DEPENDS += "enigma2 virtual/kodi"
RDEPENDS_${PN} += "virtual/kodi kodi-${MACHINE}-bin"

RRECOMMENDS_${PN} = "enigma2-plugin-extensions-subssupport"

PROVIDES += "enigma2-plugin-extensions-kodi"
RPROVIDES_${PN} += "enigma2-plugin-extensions-kodi"

SRCREV = "5d4ce1dc63d58b7d693db547e347c8e5711c1061"
SRC_URI = "git://github.com/OpenSPA/kodiext.git;protocol=git;branch=master"

S = "${WORKDIR}/git"

FILES_${PN} = "${libdir}/enigma2/python/Plugins/Extensions/Kodi \
    ${bindir}/kodiext"

inherit autotools

PACKAGE_ARCH = "${MACHINE}"
