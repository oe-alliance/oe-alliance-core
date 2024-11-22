FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

RDEPENDS:${PN}-core += "python3-service-identity python3-typing-extensions"

SRC_URI += " \
           file://0001-fix-writing-after-channel-is-closed.patch \
           file://0002-Revert-Remove-twisted.web.client.getPage-and-friends.patch \
"

PR = "r8"

ALLOW_EMPTY:${PN} = "1"

include python3-package-split.inc
