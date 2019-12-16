# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

INSANE_SKIP_${PN} += "file-rdeps"
INSANE_SKIP_${PN}-tools += "file-rdeps"
