PACKAGE_ARCH = "${MACHINEBUILD}"

PR_append = ".3"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${DISTRO_NAME}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${MACHINE}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${MACHINEBUILD}:"

hostname = "${MACHINEBUILD}"

do_install_append() {
    rm -rf ${D}/autofs
    rm -rf ${D}/mnt
    rm -rf ${D}/hdd
    ln -sf media/hdd ${D}/hdd
    ln -sf media ${D}/mnt
    rm -rf ${D}/media/*
    rm -fr ${D}/tmp
    mkdir ${D}/media/net
}
