SUMMARY = "Console text editor with good functionality, good choice for vi-haters."
HOMEPAGE = "http://joe-editor.sourceforge.net/"
SECTION = "console/utils"
LICENSE = "GPL-1.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "${SOURCEFORGE_MIRROR}/joe-editor/joe-${PV}.tar.gz"
SRC_URI[md5sum] = "3c3b6d5089a29ddc746ee89bab59286e"
SRC_URI[sha256sum] = "c556adff77fd97bf1b86198de6cb82e0b92cda18579c4fef6c83b608d2ed2915"

inherit autotools update-alternatives

RDEPENDS_${PN} = "ncurses-terminfo"

EXTRA_OECONF_prepend="--bindir=/bin"

ALTERNATIVE_${PN} = "editor"
ALTERNATIVE_LINK_NAME[editor] = "${base_bindir}/editor"
ALTERNATIVE_TARGET[editor] = "${base_bindir}/joe"
ALTERNATIVE_PRIORITY = "125"
