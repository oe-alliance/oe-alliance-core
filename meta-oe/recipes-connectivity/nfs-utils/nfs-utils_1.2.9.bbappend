PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r2"

RDEPENDS_${PN}-client = "rpcbind"
RDEPENDS_${PN} = "${PN}-client"

INITSCRIPT_PARAMS = "defaults 11"
