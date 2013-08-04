SUMMARY = "NCurses Disk Usage"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=321d4ec4e1e7746028446d003a975868"
DEPENDS = "ncurses"
PR = "r1"

SRC_URI = "http://dev.yorhel.nl/download/ncdu-${PV}.tar.gz"
SRC_URI[md5sum] = "94d7a821f8a0d7ba8ef3dd926226f7d5"
SRC_URI[sha256sum] = "42aaf0418c05e725b39b220166a9c604a9c54c0fbf7692c9c119b36d0ed5d099"

inherit autotools
