inherit upx-compress

PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "3.6.0"
SRC_URI[sha256sum] = "b6a5f44b7eb69e3fa35dbf15524405b44837a481d43d81daddde3ff21fcbb8e9"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://openssl-cnf.patch"
