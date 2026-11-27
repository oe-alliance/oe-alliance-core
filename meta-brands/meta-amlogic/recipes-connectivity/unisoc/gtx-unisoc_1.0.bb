SUMMARY = "UniSoc SDIO WLAN and Bluetooth transport for the GTX Combo"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://BSP/platform/wcn_boot.c;beginline=1;endline=12;md5=e5e65bcab12f13ef102dfefc3e65f081"
COMPATIBLE_MACHINE = "^gtxcombo$"

UNISOC_REV = "08165b5d56f46b569ee6461d7082ff795efafb2e"
SRC_URI = "${AMLOGIC_SOURCE_MIRROR}/uwe5631-aml-${UNISOC_REV}.tar.gz"
SRC_URI += "file://0001-unisoc-amlogic-irq-trigger.patch file://0002-gtx-sdio-firmware-crc.patch file://gtx-wireless"
SRC_URI[sha256sum] = "104b06b7e21df7b5c23531186fd8f3564c34d2b1f06e61ad3acc4f5ac8b0a4d0"
S = "${UNPACKDIR}/uwe5631-aml-${UNISOC_REV}"
PR = "r1"

inherit module update-rc.d
INITSCRIPT_NAME = "gtx-wireless"
INITSCRIPT_PARAMS = "start 18 2 3 4 5 . stop 82 0 1 6 ."

EXTRA_OEMAKE += "-C ${STAGING_KERNEL_DIR} KERNEL_SRC=${STAGING_KERNEL_DIR} CFG_AML_WIFI_DEVICE_UWE5621=y \
    UNISOC_BSP_INCLUDE=${S}/BSP/include UNISOC_WIFI_CUS_CONFIG=/lib/firmware/uwewifi \
    UNISOC_FW_PATH_CONFIG=/lib/firmware/"

do_compile() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    for subdir in BSP WIFI BT/tty-sdio; do
        oe_runmake M=${S}/$subdir O=${STAGING_KERNEL_BUILDDIR} CURFOLDER=${S}/BSP \
            CC="${KERNEL_CC}" LD="${KERNEL_LD}" AR="${KERNEL_AR}" modules
    done
}

do_install() {
    for mod in BSP/uwe5621_bsp_sdio WIFI/sprdwl_ng BT/tty-sdio/sprdbt_tty; do
        install -Dm0644 ${S}/$mod.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/${mod##*/}.ko
    done
    install -d ${D}${nonarch_base_libdir}/firmware/unisoc
    install -Dm0755 ${UNPACKDIR}/gtx-wireless ${D}${sysconfdir}/init.d/gtx-wireless
    ln -s ../wcnmodem.bin ${D}${nonarch_base_libdir}/firmware/unisoc/wcnmodem.bin
    install -m0644 ${S}/BT/libbt/conf/sprd/runtime/bt_configure_*.ini ${D}${nonarch_base_libdir}/firmware/unisoc/
}

FILES:${PN} += "${nonarch_base_libdir}/firmware/unisoc ${sysconfdir}/init.d/gtx-wireless"
RDEPENDS:${PN} += "firmware-uwe5621 kernel-module-uwe5621-bsp-sdio kernel-module-sprdwl-ng kernel-module-sprdbt-tty"
RDEPENDS:${PN} += "bluez5 kernel-module-hci-uart"
