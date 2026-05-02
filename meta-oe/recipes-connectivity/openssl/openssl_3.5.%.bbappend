inherit upx-compress

PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "4.0.0"
SRC_URI[sha256sum] = "c32cf49a959c4f345f9606982dd36e7d28f7c58b19c2e25d75624d2b3d2f79ac"

SRC_URI:remove = "file://0001-Added-handshake-history-reporting-when-test-fails.patch"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://openssl-cnf.patch"
