INSANE_SKIP_${PN} += "file-rdeps"
INSANE_SKIP_${PN}-tools += "file-rdeps"

# Qt packages are machine specific
QT_PACKAGES_ARCH = "${MACHINE_ARCH}"