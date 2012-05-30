PRINC = "3"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
	rm -rf ${D}/mnt
	rm -rf ${D}/hdd
	ln -sf media/hdd ${D}/hdd
	ln -sf media ${D}/mnt
	rm -rf ${D}/media/*
	rm -fr ${D}/tmp
}
