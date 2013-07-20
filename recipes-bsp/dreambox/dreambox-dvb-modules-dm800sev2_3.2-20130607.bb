require dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "0572967efaa23ea8c8a0a74977c64616"
SRC_URI[modules.sha256sum] = "3267f86966076abf7ab7e0f6e13620d8fed55dfddf22a91dda64fe047b5d4496"

RDEPENDS_${PN} += "kernel-module-stv0299"
