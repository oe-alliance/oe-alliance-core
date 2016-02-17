SUMMARY = "Hardware drivers for Dreambox"
SECTION = "base"
LICENSE = "CLOSED"
DEPENDS += "virtual/kernel"
PRIORITY = "required"
INC_PR = "r9"
require conf/license/license-close.inc

DREAMBOX_DVB_MODULES_MIRROR ?= "http://dreamboxupdate.com/download/opendreambox/2.2.0/dreambox-dvb-modules/${KV}-${MACHINE}-${DRIVERDATE}/${MACHINE}/${MD5SUM}"

SRC_URI = " \
            ${DREAMBOX_DVB_MODULES_MIRROR}/dreambox-dvb-modules_${KV}-${MACHINE}-${DRIVERDATE}_${MACHINE}.tar.xz;name=${MACHINE} \
            file://modules \
"

S = "${WORKDIR}/dreambox-dvb-modules_${KV}-${MACHINE}-${DRIVERDATE}_${MACHINE}/lib/modules/${DM_LOCALVERSION}/extra"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}${sysconfdir}/modules-load.d
    install -m 0644 ${WORKDIR}/modules ${D}${sysconfdir}/modules-load.d/${PN}.conf
    install -d ${D}/lib/modules/${DM_LOCALVERSION}/extra
    install -m 0644 ${S}/LICENSE ${D}/lib/modules/${DM_LOCALVERSION}/extra
    install -m 0644 ${S}/*.ko ${D}/lib/modules/${DM_LOCALVERSION}/extra
    install -m 0644 ${S}/nims/si2166b/*.ko ${D}/lib/modules/${DM_LOCALVERSION}/extra
	install -m 0644 ${S}/nims/si2169c/*.ko ${D}/lib/modules/${DM_LOCALVERSION}/extra
}

PACKAGES = "${PN}"

RDEPENDS_${PN} += "dreambox-secondstage-${MACHINE} kernel-${DM_LOCALVERSION}"

# We don't use KERNEL_VERSION in this recipe, because the
# precompiled modules depend on a specific version.
DM_LOCALVERSION = "${@'-'.join('${PV}'.split('-')[:-1])}-${MACHINE}"
DRIVERDATE = "${@'${PV}'.split('-')[-1]}"
MD5SUM = "${@d.getVarFlag('SRC_URI', 'dm820.md5sum')}"

FILESEXTRAPATHS_prepend := "${THISDIR}/dreambox-dvb-modules:"

FILES_${PN} += "${sysconfdir}/modules-load.d/${PN}.conf"

