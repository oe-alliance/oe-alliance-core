LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=af88d758f75f3c5c48a967501f24384b"
PV = "9.1.0"
SRCREV = "a33701196adfad74917046096bf5a2aa0ab0bb50"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-CMakeLists.txt-fix-library-install-path.patch"

FILES:${PN}-dev += "${libdir}/cmake"

EXTRA_OECMAKE = "-DBASE_LIB_PATH=${baselib}"
