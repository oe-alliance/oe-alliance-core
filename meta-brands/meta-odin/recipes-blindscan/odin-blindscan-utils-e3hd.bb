SUMMARY = "Utils for DVB-S blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "3.6.0"
SRC = "1213"
PR = "r3"

require odin-blindscan-utils.inc

SRC_URI[md5sum] = "e6718bcdb6d451d7cb7b9cd1ddaa6721"
SRC_URI[sha256sum] = "4aec7a593d2030c3c219ae3604fb56b9d3366c2e8edf4bd2b7a552e2c4caaa8e"
