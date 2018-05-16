SUMMARY = "Hardkernel Enigma2 wifi driver for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;md5=10b273937357934b8f2e2d60d645e23b"

PR = "r0"
KV = "3.14.79"

SRC_URI[brcm.md5sum] = "751bc7b516f83c1541b84a11438ec976"
SRC_URI[aml.md5sum] = "7d5b0e02822baaa65a1030e35c9186f4"

SRC_URI = "http://sources.libreelec.tv/devel/brcmap6xxx-aml-1.201.59.5-b82e63f.tar.xz;name=brcm \
	   http://sources.libreelec.tv/devel/wlan-firmware-aml-b74369c.tar.xz;name=aml \
	   file://config.txt \
	  "

S = "${WORKDIR}/brcmap6xxx-aml-1.201.59.5-b82e63f/bcmdhd_1_201_59_x"

inherit module

EXTRA_OEMAKE = "KDIR=${STAGING_KERNEL_DIR}"

do_compile() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake -C "${STAGING_KERNEL_DIR}" M="${S}" modules 
}

do_install() {
	install -d ${D}${nonarch_base_libdir}/modules/${KV}/bcmdhd
	install -m 0644 ${S}/dhd.ko ${D}${nonarch_base_libdir}/modules/${KV}/bcmdhd

	install -d ${D}${nonarch_base_libdir}/firmware/brcm
	install -m 0644 ${WORKDIR}/config.txt ${D}${nonarch_base_libdir}/firmware/brcm
	install -m 0644 ${WORKDIR}/wlan-firmware-aml-b74369c/bcm_ampak/config/AP6330/Wi-Fi/fw_bcm40183b2*.bin ${D}${nonarch_base_libdir}/firmware/brcm
	install -m 0644 ${WORKDIR}/wlan-firmware-aml-b74369c/bcm_ampak/config/AP6330/Wi-Fi/nvram_ap6330.txt ${D}${nonarch_base_libdir}/firmware/brcm
	install -m 0644 ${WORKDIR}/wlan-firmware-aml-b74369c/bcm_ampak/config/AP6330/BT/bcm40183b2.hcd ${D}${nonarch_base_libdir}/firmware/brcm
	install -m 0644 ${WORKDIR}/wlan-firmware-aml-b74369c/bcm_ampak/config/6335/fw_bcm4339a0_*.bin ${D}${nonarch_base_libdir}/firmware/brcm
	install -m 0644 ${WORKDIR}/wlan-firmware-aml-b74369c/bcm_ampak/config/6335/nvram.txt ${D}${nonarch_base_libdir}/firmware/brcm/nvram_ap6335.txt
	install -m 0644 ${WORKDIR}/wlan-firmware-aml-b74369c/bcm_ampak/config/6335/BT/bcm4335c0.hcd ${D}${nonarch_base_libdir}/firmware/brcm
}

FILES_${PN} += "${nonarch_base_libdir}/firmware/brcm/*"

