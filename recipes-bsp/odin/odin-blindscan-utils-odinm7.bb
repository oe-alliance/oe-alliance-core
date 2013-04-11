DESCRIPTION = "Utils for DVB-S blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "3.6.0"
PR = "r0"

require odin-blindscan-utils.inc

SRC_URI[md5sum] = "15ee1e8247900fec1e096a35262555f7"
SRC_URI[sha256sum] = "a1f532e3c5bd36eae48da25ad1a7e7a5f25d74434fd9102bc25f4a27047f3f8c"
