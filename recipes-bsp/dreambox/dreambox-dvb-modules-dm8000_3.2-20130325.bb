require dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "363a7d7d25dfab452172256fdcc04920"
SRC_URI[modules.sha256sum] = "fd27f110aabb5c83a106b4414d949476151527cbf39c3d5c04daa4c0c5ffb2f0"

RDEPENDS_${PN} += "kernel-module-stv0299"
