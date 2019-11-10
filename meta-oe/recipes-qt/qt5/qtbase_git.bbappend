FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"

SRC_URI_append = "file://mkspecs-fix-build-with-gcc9.patch \
                file://0001-implement-openssl111.patch"

CPPFLASGS += "-Wno-class-memaccess -Wno-implicit-fallthgrouh -Wno-unused-variable"
CXXFLASGS += "-Wno-class-memaccess -Wno-implicit-fallthgrouh -Wno-unused-variable"

INSANE_SKIP_${PN} += "file-rdeps"
INSANE_SKIP_${PN}-plugins += "file-rdeps"

# Qt packages are machine specific
QT_PACKAGES_ARCH = "${MACHINE_ARCH}"

EXTRA_QTBASE ?= "empty"
EXTRA_QTBASE_vusolose = "vuplus"
EXTRA_QTBASE_vuduo2 = "vuplus"
EXTRA_QTBASE_vusolo2 = "vuplus"
EXTRA_QTBASE_vusolo4k = "vuplus"
EXTRA_QTBASE_vuultimo4k = "vuplus"
EXTRA_QTBASE_vuuno4k = "vuplus"
EXTRA_QTBASE_vuuno4kse = "vuplus"
EXTRA_QTBASE_vuzero4k = "vuplus"
EXTRA_QTBASE_vuduo4k = "vuplus"

require qtbase-${EXTRA_QTBASE}_git.inc
