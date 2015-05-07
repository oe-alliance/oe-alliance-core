SUMMARY = "Hardware drivers and blindscan util for Vuplus Tuner Turbo"
SECTION = "base"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCDATE = "20150303"
FW_SRCDATE = "20150303"
PV = "${KV}"
PR = "${SRCDATE}"

SRC_URI = " \
    http://archive.vuplus.com/download/usb/tuner/turbo/vuplus_tuner_turbo_driver_${PV}_${SRCDATE}.tar.gz;name=driver \
    http://archive.vuplus.com/download/usb/tuner/turbo/vuplus_tuner_turbo_bin_fw_${FW_SRCDATE}.tar.gz;name=binfw \
"

DEPENDS += "virtual/kernel module-init-tools"
RDEPENDS_${PN} += "module-init-tools-depmod"

S = "${WORKDIR}"

inherit module-base

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/media/usb/vuplus
    install -m 0644 ${S}/vuplus_tuner_turbo_driver.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/media/usb/vuplus/vuplus_tuner_turbo_driver.ko
    install -d ${D}/lib/firmware/
    install -m 0644 ${S}/Vuplus_Turbo_USB_0101.fw ${D}/lib/firmware/Vuplus_Turbo_USB_0101.fw
    install -d "${D}/${bindir}"
    install -m 0755 "${S}/vuplus_turbo_c" "${D}/${bindir}"
    install -m 0755 "${S}/vuplus_turbo_t" "${D}/${bindir}"
}

pkg_postinst_${PN} () {
    if [ -d /proc/stb ]; then
       depmod -a
    fi
    true
}

FILES_${PN} = "/"

SRC_URI[binfw.md5sum] = "517a9d54e41fae16612bcfe431ef7a3e"
SRC_URI[binfw.sha256sum] = "b39f0b38684c44a13dd62ac4286a4a73bcf74fd25a881ecfe97d292535163ca0"

