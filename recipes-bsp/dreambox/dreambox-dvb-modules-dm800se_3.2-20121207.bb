require dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "842737462d35db0550e98a06a7f5c5b2"
SRC_URI[modules.sha256sum] = "c79dc0030da1f8e2415c42ef677eed09a4e75d901deb896fe0eb56fe581dcb93"

RDEPENDS_${PN} += "kernel-module-stv0299"
