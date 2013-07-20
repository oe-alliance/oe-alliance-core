require recipes-bsp/dreambox/dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "a6861487a2603f6950cb75279a9cbdfe"
SRC_URI[modules.sha256sum] = "2c031d1b3698d788f114cbb7799c9aaeed91b349d8f0687cd81e33569e53d24f"

RDEPENDS_${PN} += "kernel-module-stv0299"
