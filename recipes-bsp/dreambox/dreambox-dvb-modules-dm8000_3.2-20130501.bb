require recipes-bsp/dreambox/dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "349e41e6951b867f8c34b29a934a7c0c"
SRC_URI[modules.sha256sum] = "298eb580fa205b99c2522d2f8069567ea5e032f8bc0fc81e7a4504e634403cbb"

RDEPENDS_${PN} += "kernel-module-stv0299"
