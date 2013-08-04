SUMMARY = "Console text editor with good functionality, good choice for vi-haters."
HOMEPAGE = "http://joe-editor.sourceforge.net/"
SECTION = "console/utils"
LICENSE = "GPL-1.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=da10ed7cf8038981c580e11c1d3e8fb6"
PR = "r2"

SRC_URI = "${SOURCEFORGE_MIRROR}/joe-editor/joe-${PV}.tar.gz"
SRC_URI[md5sum] = "66de1b073e869ba12abbfcde3885c577"
SRC_URI[sha256sum] = "cae456e1ad5a8c1d3c94920a3416c2347277739b260e3494d3bc0f2b9b73106f"

inherit autotools

RDEPENDS_${PN} = "ncurses-terminfo"
