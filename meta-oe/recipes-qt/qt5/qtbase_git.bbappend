FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-implement-openssl111.patch"

CPPFLASGS += "-Wno-class-memaccess -Wno-implicit-fallthgrouh -Wno-unused-variable"
CXXFLASGS += "-Wno-class-memaccess -Wno-implicit-fallthgrouh -Wno-unused-variable"

INSANE_SKIP_${PN} += "file-rdeps"
INSANE_SKIP_${PN}-plugins += "file-rdeps"

# Qt packages are machine specific
QT_PACKAGES_ARCH = "${MACHINE_ARCH}"
