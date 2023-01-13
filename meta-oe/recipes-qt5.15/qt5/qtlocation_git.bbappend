# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

INSANE_SKIP:${PN} += "file-rdeps"
