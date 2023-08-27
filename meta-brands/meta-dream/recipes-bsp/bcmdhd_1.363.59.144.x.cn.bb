require bcmdhd.inc

LIC_FILES_CHKSUM = "file://${M}/dhd_linux.c;endline=29;md5=8a24c5318b99277c9cc40f18dbf7731c"

SRCREV = "38a2e98"

SRC_URI = "https://source.mynonpublic.com/dreambox/${BPN}.${PV}-${SRCREV}.tar.xz \
           file://0001-Makefile-set-default-firmware-path.patch"
SRC_URI[md5sum] = "6c4425868dd86785f1986ce7642685f1"
SRC_URI[sha256sum] = "177af925bc2e7130e59dc56b0e6f13b992fee8ab57e7b221671e5cb2ec37e008"

S = "${WORKDIR}/${BPN}.${PV}"

COMPATIBLE_MACHINE = "^(meson64)$"

export KCFLAGS += " -Wno-error=stringop-overflow -Wno-error=address"
