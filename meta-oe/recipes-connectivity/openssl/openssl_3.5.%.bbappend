inherit upx-compress

PACKAGE_ARCH = "${MACHINE_ARCH}"

PR .= ".1"

PV = "3.5.2"
SRC_URI[sha256sum] = "c53a47e5e441c930c3928cf7bf6fb00e5d129b630e0aa873b08258656e7345ec"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://openssl-cnf.patch"
