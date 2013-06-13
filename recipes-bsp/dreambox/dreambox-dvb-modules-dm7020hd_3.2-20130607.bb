require recipes-bsp/dreambox/dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "0b0296232c0d3a4a4b90729fb7dcd501"
SRC_URI[modules.sha256sum] = "87750816e0a12e004d37e07f03d29b687e4aa222364c2d3a9bc57088723cf880"

RDEPENDS_${PN} += "kernel-module-stv0299"
