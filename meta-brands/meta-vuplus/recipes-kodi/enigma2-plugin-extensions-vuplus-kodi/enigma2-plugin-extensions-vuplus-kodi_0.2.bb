DESCRIPTION = "Enigma2 plugin to launch and play media from Kodi"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
PR = "r0"

RRECOMMENDS_${PN} = "enigma2-plugin-extensions-subssupport"

SRCREV = "1dd3e0696fe42af4cfb48eb49ebddeb5bd2c0207"
SRC_URI = "git://github.com/mx3L/kodiext;protocol=git;branch=master \
	file://videomode.patch"

S = "${WORKDIR}/git"

FILES_${PN} = "${libdir}/enigma2/python/Plugins/Extensions/Kodi \
    ${bindir}/kodiext"

inherit autotools
