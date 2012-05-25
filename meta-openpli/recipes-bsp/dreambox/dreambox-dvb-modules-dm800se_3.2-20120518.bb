require dreambox-dvb-modules.inc

PR = "${INC_PR}.1"

SRC_URI[modules.md5sum] = "4e93b8762452e3137fb0db3c0b886d44"
SRC_URI[modules.sha256sum] = "234f4bbbd2a081771947c9394c94ba1d68dbdcce207c2770b6b338d583e68c9d"

RDEPENDS_${PN} += "kernel-module-stv0299"
