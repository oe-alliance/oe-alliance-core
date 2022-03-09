FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

RDEPENDS:${PN}-core += "${PYTHON_PN}-service-identity ${PYTHON_PN}-typing-extensions"

SRC_URI += " \
           file://0001-fix-writing-after-channel-is-closed.patch \
           file://0002-Revert-Remove-twisted.web.client.getPage-and-friends.patch \
"

PR = "r8"

ALLOW_EMPTY:${PN} = "1"

include ${PYTHON_PN}-package-split.inc
