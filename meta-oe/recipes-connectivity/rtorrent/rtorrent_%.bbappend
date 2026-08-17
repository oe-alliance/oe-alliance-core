FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " file://0001-configure-add-MIPS-to-the-Linux-cacheline-fallback.patch"
