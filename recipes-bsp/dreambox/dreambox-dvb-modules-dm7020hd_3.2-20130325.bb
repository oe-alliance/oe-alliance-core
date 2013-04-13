require dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "61fe051c9005cacd923c4a4f0a14e831"
SRC_URI[modules.sha256sum] = "476019d60cc98e24b0915c3c56cadce09572c6faa3264a6ea10169390dc92110"

RDEPENDS_${PN} += "kernel-module-stv0299"
