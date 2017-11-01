FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

RDEPENDS_${PN}-client = "rpcbind"
RDEPENDS_${PN} = "${PN}-client"
RRECOMMENDS_${PN}-client = "kernel-module-nfs kernel-module-exportfs"

INITSCRIPT_PARAMS = "defaults 11"

do_install_append() {
	sed -e 's:!/bin/bash:!/bin/sh:' -i ${D}${base_sbindir}/osd_login
}

SRC_URI_append = " file://fix_build_with_glibc_header.patch"