FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-CMakeLists.txt-fix-library-install-path.patch"

FILES_${PN}-dev += "${libdir}/cmake"

EXTRA_OECMAKE = "-DBASE_LIB_PATH=${baselib}"
