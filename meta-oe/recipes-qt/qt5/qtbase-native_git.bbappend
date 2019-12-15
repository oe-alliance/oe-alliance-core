FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"

SRC_URI_remove = "file://0012-qdbuscpp2xml.pro-do-not-build-with-bootstrapped-depe.patch"

SRC_URI_append = " ${@bb.utils.contains_any("MACHINE", "gb72604 gb7252", "file://mkspecs-fix-build-with-gcc9-qt5.8.patch", "file://mkspecs-fix-build-with-gcc9.patch", d)}"

