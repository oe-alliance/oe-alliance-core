FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append:gtxcombo = " file://0001-gtx-select-receiver-remote.patch"
EXTRA_OECONF:append:gtxcombo = " --with-fbdev=/dev/fb0"
