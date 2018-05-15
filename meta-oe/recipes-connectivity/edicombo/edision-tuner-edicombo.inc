SUMMARY = "Hardware drivers and firmware for Edision edicombo Tuner"
SECTION = "base"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE_ARCH}"

FW_SRCDATE = "20170526"
PV = "${KV}"
PR = "${SRCDATE}"

SRC_URI = " \
    http://source.mynonpublic.com/edicombo/dvb-usb-edicombo_${PV}_${SRCDATE}.zip;name=driver \
    http://source.mynonpublic.com/edicombo/edicombo-firmware_${FW_SRCDATE}.zip;name=binfw \
"
DEPENDS += "virtual/kernel module-init-tools"
RDEPENDS_${PN} += "module-init-tools-depmod"

S = "${WORKDIR}"

inherit module-base

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/media/usb/edision
    install -m 0644 ${S}/dvb-usb-edicombo.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/media/usb/edision/dvb-usb-edicombo.ko
    install -d ${D}${nonarch_base_libdir}/firmware/
    install -m 0644 ${S}/edicombo_usb_dvb.fw ${D}${nonarch_base_libdir}/firmware/edicombo_usb_dvb.fw
}

pkg_postinst_${PN} () {
    if [ -d /proc/stb ]; then
       depmod -a
    fi
    true
}

FILES_${PN} = "/"

SRC_URI[binfw.md5sum] = "a7c7960bdae473e4b7166d79a61bb3bb"
SRC_URI[binfw.sha256sum] = "138813f83cfc5c17ddeedbc658b07c501d59009e552fd96b256cdb04de987a08"
