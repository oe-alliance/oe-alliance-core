include ${PYTHON_PN}-package-split.inc

RDEPENDS:${PN}-core += "${PYTHON_PN}-service-identity ${PYTHON_PN}-typing-extensions"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
           file://fix-writing-after-channel-is-closed.patch \
"

PR = "r6"
