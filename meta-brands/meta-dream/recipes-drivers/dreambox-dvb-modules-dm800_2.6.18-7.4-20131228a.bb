SUMMARY = "Hardware drivers for Dreambox"
SECTION = "base"
LICENSE = "CLOSED"
DEPENDS += "virtual/kernel"
PRIORITY = "required"
INC_PR = "r8"
require conf/license/license-close.inc

COMPATIBLE_MACHINE = "^(dm800)$"

PR = "${INC_PR}.3"

SRC_URI[modules.md5sum] = "ed50381eb09c8278ebc3decba2d0c8e9"
SRC_URI[modules.sha256sum] = "d85e9968ea19a1394ccde7bcf0f57c976e169ba9581eda52d9a979c49541b295"

SRC_URI = "https://source.mynonpublic.com/dreambox/dreambox-dvb-modules-${MACHINE}-${DM_LOCALVERSION}-${DRIVERDATE}.zip;name=modules \
       file://modules \
"

inherit module

do_compile() {
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_install() {
    install -d ${D}${sysconfdir}/modules-load.d
    install -m 0644 ${WORKDIR}/modules ${D}${sysconfdir}/modules-load.d/${PN}.conf
    install -d ${D}/lib/modules/${DM_LOCALVERSION}/extra
    install -m 0644 ${WORKDIR}/LICENSE ${D}/lib/modules/${DM_LOCALVERSION}/extra
    install -m 0644 ${WORKDIR}/*.ko ${D}/lib/modules/${DM_LOCALVERSION}/extra
}

PACKAGES = "${PN}"

RDEPENDS:${PN} += "dreambox-secondstage-${MACHINE} kernel-${DM_LOCALVERSION}"

# We don't use KERNEL_VERSION in this recipe, because the
# precompiled modules depend on a specific version.
DM_LOCALVERSION = "${@'-'.join('${PV}'.split('-')[:-1])}-${MACHINE}"
DRIVERDATE = "${@'${PV}'.split('-')[-1]}"

FILESEXTRAPATHS:prepend := "${THISDIR}/dreambox-dvb-modules:"

FILES:${PN} += "${sysconfdir}/modules-load.d/${PN}.conf"
