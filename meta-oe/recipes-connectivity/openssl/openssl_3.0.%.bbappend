inherit upx-compress

PACKAGE_ARCH = "${MACHINE_ARCH}"

LDFLAGS:sh4 += "-latomic"
