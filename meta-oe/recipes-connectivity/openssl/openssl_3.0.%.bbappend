inherit upx-compress

PACKAGE_ARCH = "${MACHINE_ARCH}"

LDFLAGS:sh4 += "-latomic"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://openssl-cnf.patch"
