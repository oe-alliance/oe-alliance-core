FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

RDEPENDS_${PN}-client = "rpcbind"
RDEPENDS_${PN} = "${PN}-client"
RRECOMMENDS_${PN}-client = "kernel-module-nfs kernel-module-exportfs"

INITSCRIPT_PARAMS = "defaults 13"
INITSCRIPT_PARAMS_${PN}-client = "defaults 19 11"

do_install_append() {
	sed -e 's:!/bin/bash:!/bin/sh:' -i ${D}${base_sbindir}/osd_login
	if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
		rm ${D}${sysconfdir}/init.d/nfscommon
		rm ${D}${sysconfdir}/init.d/nfsserver
	fi
}

SRC_URI_append = " file://fix_build_with_glibc_header.patch"
