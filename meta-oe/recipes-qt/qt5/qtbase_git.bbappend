FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"

SRC_URI_append = "file://mkspecs-fix-build-with-gcc9.patch \
                file://0001-implement-openssl111.patch"

CPPFLASGS += "-Wno-class-memaccess -Wno-implicit-fallthgrouh -Wno-unused-variable"
CXXFLASGS += "-Wno-class-memaccess -Wno-implicit-fallthgrouh -Wno-unused-variable"

INSANE_SKIP_${PN} += "file-rdeps"
INSANE_SKIP_${PN}-plugins += "file-rdeps"

# Qt packages are machine specific
QT_PACKAGES_ARCH = "${MACHINE_ARCH}"
