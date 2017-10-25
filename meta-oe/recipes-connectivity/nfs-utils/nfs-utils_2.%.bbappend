RDEPENDS_${PN}-client = "rpcbind"
RDEPENDS_${PN} = "${PN}-client"
RRECOMMENDS_${PN}-client = "kernel-module-nfs kernel-module-exportfs"

INITSCRIPT_PARAMS = "defaults 11"

do_install_append() {
	sed -e 's:!/bin/bash:!/bin/sh:' -i ${D}${base_sbindir}/osd_login
}

