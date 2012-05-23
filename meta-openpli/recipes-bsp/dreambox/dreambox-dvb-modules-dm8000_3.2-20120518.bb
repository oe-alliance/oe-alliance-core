require dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "d6aa6ad1c10902b7a9b1771c0e1aa9ae"
SRC_URI[modules.sha256sum] = "4fe53f87c9babf7ba45d42e32354ded94e35f81da63445d225b8cb47088e1f27"

RDEPENDS_${PN} += "kernel-module-stv0299"
