PRINC = "10"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${DISTRO_NAME}:"
FILESEXTRAPATHS_prepend_azboxhd := "${THISDIR}/${MACHINE}:"

do_install_append() {
	rm -rf ${D}/mnt
	rm -rf ${D}/hdd
	ln -sf media/hdd ${D}/hdd
	ln -sf media ${D}/mnt
	rm -rf ${D}/media/*
	rm -fr ${D}/tmp
	mkdir ${D}/media/net
}
