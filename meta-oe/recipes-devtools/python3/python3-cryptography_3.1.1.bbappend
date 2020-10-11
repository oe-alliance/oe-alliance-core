FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-dont-check-and-warn-about-ssl-102-deprecation.patch"

include ${PYTHON_PN}-package-split.inc
