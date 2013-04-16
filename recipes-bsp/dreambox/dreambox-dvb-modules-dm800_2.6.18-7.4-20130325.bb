require recipes-bsp/dreambox/dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "a793d99e7ccc9d6b77f6b7d67d243456"
SRC_URI[modules.sha256sum] = "5bb68f863da9cd38b100fff6935bc6a67483a9b18c7c3fa88c12939a8eb1e563"

#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
