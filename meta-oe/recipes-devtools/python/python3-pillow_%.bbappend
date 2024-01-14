FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

include ${PYTHON_PN}-package-split.inc

DEPENDS += " libwebp "

PR .= ".1"
