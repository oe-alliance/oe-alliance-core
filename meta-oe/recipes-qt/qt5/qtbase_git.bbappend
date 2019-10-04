FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"

SRC_URI_append = " file://mkspecs-fix-build-with-gcc9.patch"

INSANE_SKIP_${PN} += "file-rdeps"
INSANE_SKIP_${PN}-plugins += "file-rdeps"

# Qt packages are machine specific
QT_PACKAGES_ARCH = "${MACHINE_ARCH}"
