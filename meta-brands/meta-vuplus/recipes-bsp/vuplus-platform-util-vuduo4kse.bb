require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4kse)$"

RDEPENDS:${PN} += "mmc-utils"

PV = "17.1"
SRCDATE = "20250528"
SRCDATE_PR = "r0"
PR:append = ".0"

S = "${UNPACKDIR}/platform-util-vuduo4kse"

SRC_URI[md5sum] = "9e52548331d766fb7b59c4bd800fff9b"
SRC_URI[sha256sum] = "71bdd338140b8a5bc33afaf6d634c6ca6c93f58c68edd3be91929290b25811cd"