SUMMARY = "Hardware drivers for Dreambox"
SECTION = "base"
LICENSE = "CLOSED"
DEPENDS = "virtual/kernel virtual/${TARGET_PREFIX}gcc"
PRIORITY = "required"
INC_PR = "r8"
require conf/license/license-close.inc

PRECOMPILED_ARCH ?= "${MACHINE}"
PRECOMPILED_VERSION ?= "${PV}"

DREAMBOX_DVB_MODULES_MIRROR ?= "https://source.mynonpublic.com/dreambox"

SRC_URI = " \
            ${DREAMBOX_DVB_MODULES_MIRROR}/dreambox-dvb-modules-${PRECOMPILED_ARCH}-${DM_LOCALVERSION}-${DRIVERDATE}.tar.bz2;name=modules \
            file://modules \
"

do_compile() {
}
do_populate_sysroot() {
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_install() {
    install -d ${D}${sysconfdir}/modules-load.d
    install -m 0644 ${UNPACKDIR}/modules ${D}${sysconfdir}/modules-load.d/${PN}.conf
    install -d ${D}/lib/modules/${DM_LOCALVERSION}/extra
    install -m 0644 ${S}/LICENSE ${D}/lib/modules/${DM_LOCALVERSION}/extra
    install -m 0644 ${S}/*.ko ${D}/lib/modules/${DM_LOCALVERSION}/extra
}

PACKAGES = "${PN}"

RDEPENDS:${PN} += "dreambox-secondstage-${MACHINE} kernel-vmlinux"

# We don't use KERNEL_VERSION in this recipe, because the
# precompiled modules depend on a specific version.
DM_LOCALVERSION = "${@'-'.join('${PV}'.split('-')[:-1])}-${PRECOMPILED_ARCH}"
DRIVERDATE = "${@'${PV}'.split('-')[-1]}"

FILESEXTRAPATHS:prepend := "${THISDIR}/dreambox-dvb-modules:"

FILES:${PN} += "${sysconfdir}/modules-load.d/${PN}.conf /lib/modules/${DM_LOCALVERSION}/extra"
