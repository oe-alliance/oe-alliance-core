FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PRINC = "1"

SRC_URI_append = " file://main.patch"

