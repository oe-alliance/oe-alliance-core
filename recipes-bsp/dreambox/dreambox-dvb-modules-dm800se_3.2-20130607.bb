require dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "5bfa0b9a37c138d80fe0970adf362f51"
SRC_URI[modules.sha256sum] = "dd84ff7362f52510bdff6f8e53b873e9a0b4b25ee2976b68ce645a8d636fb5bb"

RDEPENDS_${PN} += "kernel-module-stv0299"
