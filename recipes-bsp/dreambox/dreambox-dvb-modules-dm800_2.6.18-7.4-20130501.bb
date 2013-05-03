require recipes-bsp/dreambox/dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "cd334d8f1298ea8fedae3d4786902831"
SRC_URI[modules.sha256sum] = "26f6f0efbe69a5dac85a3018dd684a84d128839cd3b5b042ff40eb8f628e150d"

#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
