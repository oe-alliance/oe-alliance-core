require python-cffi_${PV}.bb
inherit native pythonnative
DEPENDS = "python-native python-distribute-native"
RDEPENDS_${PN} = ""
PR = "r0"
