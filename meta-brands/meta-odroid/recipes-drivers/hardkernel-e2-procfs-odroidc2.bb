SUMMARY = "Hardkernel Enigma2 procfs drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

KV = "3.14.29"
SRCDATE = "20161023"

PV = "${KV}+${SRCDATE}"
PR = "r1"


SRC_URI = "file://hardkernel-e2-procfs-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0755 ${WORKDIR}/e2-procfs.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
}

do_package_qa() {
}

