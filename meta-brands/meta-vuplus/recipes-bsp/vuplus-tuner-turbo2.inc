SUMMARY = "Hardware drivers and blindscan util for Vuplus Tuner Turbo2"
SECTION = "base"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCDATE = "20201207"
FW_SRCDATE = "20201207"
PV = "${KV}"
PR = "${SRCDATE}"

SRC_URI = " \
	https://source.mynonpublic.com/vuplus/release/turbo/vuplus_tuner_turbo2_driver_${PV}_${TARGET_ARCH}_${SRCDATE}.tar.gz;name=driver \
	https://source.mynonpublic.com/vuplus/release/turbo/vuplus_tuner_turbo2_bin_fw_${TARGET_ARCH}_${FW_SRCDATE}.tar.gz;name=binfw \
"

DEPENDS += "virtual/kernel module-init-tools"
RDEPENDS:${PN} += "module-init-tools-depmod"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

inherit module-base

do_install() {
        install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/media/usb/vuplus
	install -m 0644 ${S}/vuplus_tuner_turbo2_driver.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/media/usb/vuplus/vuplus_tuner_turbo2_driver.ko
	install -d ${D}/lib/firmware/
	install -m 0644 ${S}/Vuplus_Turbo2_USB_0100.fw ${D}/lib/firmware/Vuplus_Turbo2_USB_0100.fw
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/vuplus_turbo2_c" "${D}/${bindir}"
	install -m 0755 "${S}/vuplus_turbo2_t" "${D}/${bindir}"
}

pkg_postinst:${PN} () {
        if [ -d /proc/stb ]; then
                depmod -a
        fi
        true
}

FILES:${PN} = "/"

SRC_URI[binfw.md5sum] = "aa1be089011f3bdc15d88d7f70dd4c7e"
SRC_URI[binfw.sha256sum] = "9a130ee3a67994029eec37366a4cce67031d6d5e8bfda0ed8b6ce59983bbb376"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} = "ldflags"
