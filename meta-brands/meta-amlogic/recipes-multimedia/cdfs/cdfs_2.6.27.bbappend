FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:gtxcombo = " file://0001-import-vfs-namespace.patch"
MACHINE_KERNEL_PR:append:gtxcombo = ".gtx1"
