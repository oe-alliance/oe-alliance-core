include ${PYTHON_PN}-package-split.inc

RDEPENDS:${PN}-core += "${PYTHON_PN}-service-identity ${PYTHON_PN}-typing-extensions"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
           file://0001-fix-writing-after-channel-is-closed.patch \
           file://0002-Revert-Remove-twisted.web.client.getPage-and-friends.patch \
"

PR = "r7"
