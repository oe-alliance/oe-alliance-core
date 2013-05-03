require recipes-bsp/dreambox/dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "64f7632f4d0eb617bcafbaf2a2001b6c"
SRC_URI[modules.sha256sum] = "f3a80b908311ab0e5cd2e4746f40cf633280b0c48d3c4758f9e64c7fdf4b18f3"

RDEPENDS_${PN} += "kernel-module-stv0299"
