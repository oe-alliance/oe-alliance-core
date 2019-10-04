FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"

SRC_URI_remove = "file://0012-qdbuscpp2xml.pro-do-not-build-with-bootstrapped-depe.patch"

SRC_URI_append = " file://mkspecs-fix-build-with-gcc9.patch"
