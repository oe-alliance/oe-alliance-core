require recipes-bsp/dreambox/dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "f76cc880f6b65e40781e18c28159da42"
SRC_URI[modules.sha256sum] = "c23dcc9feb7e7d5e1403c6377411d2c5658cbf653ed11ab1878c4334353f8c00"

RDEPENDS_${PN} += "kernel-module-stv0299"
