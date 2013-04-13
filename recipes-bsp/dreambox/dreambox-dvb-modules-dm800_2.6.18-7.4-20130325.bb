require recipes-bsp/dreambox/dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "1cdda55131a4f538bdb6412c4c9a8186"
SRC_URI[modules.sha256sum] = "08302e8f14906d80416a85cbc779bff98452e88bb6ee4ccbe3fd9643893e4e9f"

#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
