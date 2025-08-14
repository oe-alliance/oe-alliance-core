inherit upx-compress

PACKAGE_ARCH = "${MACHINE_ARCH}"

PR .= ".1"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://openssl-cnf.patch"
