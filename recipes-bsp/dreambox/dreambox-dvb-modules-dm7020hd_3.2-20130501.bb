require recipes-bsp/dreambox/dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "b00f962221e2c140ec320379240fde84"
SRC_URI[modules.sha256sum] = "0f348b5edd086fe2a531161e6dae772ecd283c22fff08e6352a983e276164e8a"

RDEPENDS_${PN} += "kernel-module-stv0299"
