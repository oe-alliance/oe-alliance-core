PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN}-client = "rpcbind"
RDEPENDS_${PN} = "${PN}-client"

INITSCRIPT_PARAMS = "defaults 11"
