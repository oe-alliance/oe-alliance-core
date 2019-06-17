PACKAGES =+ "${PN}-dirsplit"

RDEPENDS_${PN}-dirsplit += "${PN} perl"

FILES_${PN}-dirsplit = "${bindir}/dirsplit"
