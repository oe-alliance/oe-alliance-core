SUMMARY = "Utils for DVB-S blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "3.6.0"
SRC = "1213"
PR = "r2"

require odin-blindscan-utils.inc

SRC_URI[md5sum] = "ad68cc1977fb9566f77e1b16d907b849"
SRC_URI[sha256sum] = "559c14be8db4a0174932d0415ee4275944f6809690e65c64050c9a878a680b09"
