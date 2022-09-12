PR = "r1"

RDEPENDS:${PN} = "${PN}-client"
RDEPENDS:${PN}-client = "rpcbind"
RRECOMMENDS:${PN}-client = "kernel-module-nfs kernel-module-exportfs"

INITSCRIPT_PARAMS = "defaults 13"
INITSCRIPT_PARAMS:${PN}-client = "defaults 19 11"

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        rm ${D}${sysconfdir}/init.d/nfscommon
        rm ${D}${sysconfdir}/init.d/nfsserver
    fi
}
