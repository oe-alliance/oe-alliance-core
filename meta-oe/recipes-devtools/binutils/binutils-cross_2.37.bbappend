FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://bfd-close-fd-if-no-archive-fd-exists.patch"
