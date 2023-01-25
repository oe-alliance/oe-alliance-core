SUMMARY = "NCurses Disk Usage"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=1c1e05d947d32506539c0bc71b46aa57"

DEPENDS += "ncurses"

SRC_URI = "http://dev.yorhel.nl/download/ncdu-${PV}.tar.gz"
SRC_URI[md5sum] = "4186b5be477cc3a0944619ade81f123b"
SRC_URI[sha256sum] = "3c37a1a96580c9c5d2cc352dc3c5eef0d909158c05f1cc29db4712544c8b9f95"

inherit autotools

do_configure() {
    oe_runconf
}

do_compile() {
    oe_runmake
}
