require recipes-bsp/dreambox/dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "4d639de298344c90db44070f993d86f8"
SRC_URI[modules.sha256sum] = "0183c40a1951a8c846a2409145f14736034896afd68e3b7a4aa5e8656849bcbc"

RDEPENDS_${PN} += "kernel-module-stv0299"
