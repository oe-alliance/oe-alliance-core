SUMMARY = "Hardware drivers for Dreambox"
SECTION = "base"
LICENSE = "CLOSED"
DEPENDS = "virtual/kernel virtual/${TARGET_PREFIX}gcc"
PRIORITY = "required"
INC_PR = "r8"
require conf/license/license-close.inc

PRECOMPILED_ARCH ?= "${MACHINE}"
PRECOMPILED_VERSION ?= "${PV}"

DREAMBOX_DVB_MODULES_MIRROR ?= "http://sources.dreamboxupdate.com/download/opendreambox/2.0.0/dreambox-dvb-modules"

SRC_URI = " \
            ${DREAMBOX_DVB_MODULES_MIRROR}/dreambox-dvb-modules-${PRECOMPILED_ARCH}-${DM_LOCALVERSION}-${DRIVERDATE}.tar.bz2;name=modules \
            file://modules \
"

do_compile() {
}
do_populate_sysroot() {
}

do_install() {
    install -d ${D}${sysconfdir}/modules-load.d
    install -m 0644 ${WORKDIR}/modules ${D}${sysconfdir}/modules-load.d/${PN}.conf
    install -d ${D}/lib/modules/${DM_LOCALVERSION}/extra
    install -m 0644 ${WORKDIR}/LICENSE ${D}/lib/modules/${DM_LOCALVERSION}/extra
    install -m 0644 ${WORKDIR}/*.ko ${D}/lib/modules/${DM_LOCALVERSION}/extra
}

PACKAGES = "${PN}"

RDEPENDS_${PN} += "dreambox-secondstage-${MACHINE} kernel-vmlinux"

# We don't use KERNEL_VERSION in this recipe, because the
# precompiled modules depend on a specific version.
DM_LOCALVERSION = "${@'-'.join('${PV}'.split('-')[:-1])}-${PRECOMPILED_ARCH}"
DRIVERDATE = "${@'${PV}'.split('-')[-1]}"

FILESEXTRAPATHS_prepend := "${THISDIR}/dreambox-dvb-modules:"

FILES_${PN} += "${sysconfdir}/modules-load.d/${PN}.conf /lib/modules/${DM_LOCALVERSION}/extra"
