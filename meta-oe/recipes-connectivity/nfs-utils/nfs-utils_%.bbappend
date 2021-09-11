BUILD_CFLAGS_remove = "-march=native"
BUILD_CXXFLAGS_remove = "-march=native"
CFLAGS_FOR_BUILD_remove = "-march=native"
CXXFLAGS_FOR_BUILD_remove = "-march=native"

# Commit 664ae3dc52fd7fc8c6f64e6cf5e70f97dedd332d in OE-core force-feeds
# bash into our system, which we definitely don't want to happen. This
# bbappend basically reverses that commit.
#
RDEPENDS_${PN}-client_remove = "bash"
RDEPENDS_${PN}_remove = "bash"

INSANE_SKIP_${PN} = "file-rdeps"

# The startup script does a check that doesn't work, replace it. It's
# also overly complex, so simplified it too.
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

CFLAGS_remove_sh4 = "-Wno-error=format-overflow"

RDEPENDS_${PN}-client = "rpcbind"
RDEPENDS_${PN} = "${PN}-client"
RRECOMMENDS_${PN}-client = "kernel-module-nfs kernel-module-exportfs"

INITSCRIPT_PARAMS = "defaults 13"
INITSCRIPT_PARAMS_${PN}-client = "defaults 19 11"

do_install_append() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        rm ${D}${sysconfdir}/init.d/nfscommon
        rm ${D}${sysconfdir}/init.d/nfsserver
    fi
}
