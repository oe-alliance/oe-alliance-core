require dreambox-dvb-modules.inc

PR = "${INC_PR}.1"

SRC_URI[modules.md5sum] = "39b15fb66022a1783cac5e6db25ae945"
SRC_URI[modules.sha256sum] = "427b0e0b0f687464690cc37a183c8039071e313ee067a2b54fcbef742e27b6b1"

RDEPENDS_${PN} += "kernel-module-stv0299"
