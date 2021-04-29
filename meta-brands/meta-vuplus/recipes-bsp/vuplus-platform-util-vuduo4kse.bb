require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4kse)$"

RDEPENDS_${PN} += "mmc-utils"

PV="17.1"
SRCDATE = "20210407"
SRCDATE_PR = "r0"
PR_append = ".0"

S="${WORKDIR}/platform-util-vuduo4kse"

SRC_URI[md5sum] = "5d13ee1a44003d626f008d81b952dbaf"
SRC_URI[sha256sum] = "b1a1b86b5dae533de5cc9643dc0dbb0488ab30d456a7c4bf60b966a6190953c1"