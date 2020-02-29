SUMMARY = "Small, safe and fast formatting library http://fmtlib.net"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=c2e38bc8629eac247a73b65c1548b2f0"

PV = "4.0.0"
SRC_URI = "git://github.com/fmtlib/fmt.git;protocol=https \
    file://0001-CMakeLists.txt-fix-library-install-path.patch \
"
SRCREV = "d16c4d20f88c738d79ecec7c355584f7e161e03e"

S = "${WORKDIR}/git"

inherit cmake

FILES_${PN}-dev += "${libdir}/cmake"

EXTRA_OECMAKE = "-DBASE_LIB_PATH=${baselib}"
