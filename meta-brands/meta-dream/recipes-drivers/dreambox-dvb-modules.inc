SUMMARY = "Hardware drivers for Dreambox"
SECTION = "base"
DEPENDS = "virtual/kernel ${MLPREFIX}gcc-cross-${TARGET_ARCH}"
PRIORITY = "required"
INC_PR = "r9"
require conf/license/license-close.inc

PRECOMPILED_ARCH ?= "${MACHINEBUILD}"
PRECOMPILED_VERSION ?= "${PV}"

DREAMBOX_DVB_MODULES_MIRROR ?= "https://source.mynonpublic.com/dreambox"

SRC_URI = " \
            ${DREAMBOX_DVB_MODULES_MIRROR}/dreambox-dvb-modules-${PRECOMPILED_ARCH}-${DM_LOCALVERSION}-${DRIVERDATE}.tar.bz2;name=modules \
            file://modules \
"

SRC_URI:append:dm8000 = " \
    file://dm8000-bcm7400-load \
    file://dm8000-bcm7400-watchdog.conf \
"

S = "${UNPACKDIR}"

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
    install -m 0644 ${UNPACKDIR}/LICENSE ${D}/lib/modules/${DM_LOCALVERSION}/extra
    install -m 0644 ${UNPACKDIR}/*.ko ${D}/lib/modules/${DM_LOCALVERSION}/extra
}

do_install:append:dm8000() {
    install -d ${D}${sbindir} ${D}${sysconfdir}/modprobe.d
    install -m 0755 ${UNPACKDIR}/dm8000-bcm7400-load ${D}${sbindir}/dm8000-bcm7400-load
    install -m 0644 ${UNPACKDIR}/dm8000-bcm7400-watchdog.conf ${D}${sysconfdir}/modprobe.d/dm8000-bcm7400-watchdog.conf
}

PACKAGES = "${PN}"

RDEPENDS:${PN} += " \
    dreambox-secondstage-${MACHINEBUILD} \
    kernel-vmlinux \
    kernel-module-snd-pcm \
"

# We don't use KERNEL_VERSION in this recipe, because the
# precompiled modules depend on a specific version.
DM_LOCALVERSION = "${@'-'.join('${PV}'.split('-')[:-1])}-${PRECOMPILED_ARCH}"
DRIVERDATE = "${@'${PV}'.split('-')[-1]}"

FILESEXTRAPATHS:prepend := "${THISDIR}/dreambox-dvb-modules:"

FILES:${PN} += "${sysconfdir}/modules-load.d/${PN}.conf /lib/modules/${DM_LOCALVERSION}/extra"
