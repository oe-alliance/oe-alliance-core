DESCRIPTION = "Enigma2 plugin to launch and play media from Kodi"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
PR = "r0"

COMPATIBLE_MACHINE = "^(xc7346)$"

DEPENDS += "enigma2 virtual/kodi"
RDEPENDS_${PN} += "virtual/kodi"

RRECOMMENDS_${PN} = "enigma2-plugin-extensions-subssupport"

PROVIDES += "enigma2-plugin-extensions-kodi"
RPROVIDES_${PN} += "enigma2-plugin-extensions-kodi"

SRCREV = "ae55836030e94c38557f8e1b3efd5c72f131922a"
SRC_URI = "git://github.com/mx3L/kodiext;protocol=git;branch=master"

S = "${WORKDIR}/git"

FILES_${PN} = "${libdir}/enigma2/python/Plugins/Extensions/Kodi \
    ${bindir}/kodiext"

inherit autotools
