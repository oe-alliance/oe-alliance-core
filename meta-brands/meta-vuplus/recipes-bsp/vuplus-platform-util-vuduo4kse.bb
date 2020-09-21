require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4kse)$"

RDEPENDS_${PN} += "mmc-utils"

PV="17.1"
SRCDATE = "20200903"
SRCDATE_PR = "r0"
PR_append = ".0"

S="${WORKDIR}/platform-util-vuduo4kse"

SRC_URI[md5sum] = "d40e56f73e3325bc8c7c5d1bd8ec429f"
SRC_URI[sha256sum] = "fe37ff1b6e7b456bf467e15d65fe8cf9a09ae1beccb9fa89ac9e4a514ba255c6"