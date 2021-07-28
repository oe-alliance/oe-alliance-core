# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

INSANE_SKIP_${PN}-qmlplugins += "file-rdeps"
