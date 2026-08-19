PR = "r2"

RDEPENDS:${PN} = "${PN}-client"
RDEPENDS:${PN}-client = "rpcbind"
RRECOMMENDS:${PN}-client = "kernel-module-nfs kernel-module-exportfs"

INITSCRIPT_PARAMS = "defaults 13"
INITSCRIPT_PARAMS:${PN}-client = "defaults 19 11"

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        rm ${D}${sysconfdir}/init.d/nfscommon
        rm ${D}${sysconfdir}/init.d/nfsserver
    else
        # export of /media/hdd during service startup if a local hard disk is mounted
        sed -i -e "/^\ttest -r \/etc\/exports && exportfs -r/i \\\ttest -r \/etc\/exports || { grep -q '^/dev.*/media/hdd' \/proc\/mounts && echo '/media/hdd *\(rw,no_root_squash,sync,no_subtree_check\)' > /etc/exports; }" ${D}${sysconfdir}/init.d/nfsserver
    fi

    # STBs have no RDMA hardware; rpc.nfsd otherwise warns on every start
    sed -i -e 's/^#* *rdma *=.*/rdma=n/' ${D}${sysconfdir}/nfs.conf
}
