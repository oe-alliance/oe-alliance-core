require dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "7d429ab1f0620efe3763dbdf8cdbafda"
SRC_URI[modules.sha256sum] = "5d14fb82fefe2b7c9a7ceec6f6ac255d4d2a3b4ec9099efc17943bff3c53057e"

RDEPENDS_${PN} += "kernel-module-stv0299"
