require recipes-bsp/dreambox/dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "d30090eb35d8c2c3b4699e03861e791c"
SRC_URI[modules.sha256sum] = "25429923e2b5113fcb45480f4bd67b8c219710159369d4f9d26759a715d6d45b"

RDEPENDS_${PN} += "kernel-module-stv0299"
