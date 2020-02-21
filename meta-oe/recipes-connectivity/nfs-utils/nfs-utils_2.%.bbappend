PR = "r1"

RDEPENDS_${PN} = "${PN}-client"
RDEPENDS_${PN}-client = "rpcbind"
RRECOMMENDS_${PN}-client = "kernel-module-nfs kernel-module-exportfs"

CFLAGS_remove_sh4 = "-Wno-error=format-overflow"

INITSCRIPT_PARAMS = "defaults 13"
INITSCRIPT_PARAMS_${PN}-client = "defaults 19 11"

do_install_append() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        rm ${D}${sysconfdir}/init.d/nfscommon
        rm ${D}${sysconfdir}/init.d/nfsserver
    fi
}
