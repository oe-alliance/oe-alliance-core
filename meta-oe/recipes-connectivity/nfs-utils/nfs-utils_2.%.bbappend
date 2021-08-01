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

FILESEXTRAPATHS:prepend:sh4 := "${THISDIR}/${PN}:"
SRC_URI:append:sh4 = " file://0001-sh4-fix-rpc-header-location.patch"
CFLAGS:remove:sh4 = "-Wno-error=format-overflow"
