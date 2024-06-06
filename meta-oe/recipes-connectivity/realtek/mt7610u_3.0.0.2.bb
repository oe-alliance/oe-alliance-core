DESCRIPTION = "Driver for Ralink mt7610u USB 802.11a/b/g/n/ac WiFi sticks"
HOMEPAGE = "www.mediatek.com"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://iwpriv_usage.txt;md5=8876ae2c103446a442658f1cc2a01b76"


inherit module

SRC_URI = "file://mt7610u_wifi_sta_v3002_dpo_20130916.tar.bz2 \
	file://config.patch \
	file://change_device_name.patch \
	file://firmware_file_rename.patch \
	file://new_devices.patch \
	file://buildfix.patch \
	"

SRC_URI:append:dm900 = " file://fix_build_arm.patch"
SRC_URI:append:dm920 = " file://fix_build_arm.patch"

S = "${WORKDIR}/mt7610u_wifi_sta_v3002_dpo_20130916"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR}"

# need only for dreambox linux-meson64 4.9
export KCFLAGS += " -Wno-error=misleading-indentation \
                    -Wno-error=aggressive-loop-optimizations \
                    -Wno-error=int-to-pointer-cast \
                    -Wno-error=restrict \
                    -Wno-error=int-conversion \
                    -Wno-error=maybe-uninitialized \
                    -Wno-error=discarded-qualifiers \
                    -Wno-error=switch-unreachable \
                    -Wno-error=bool-operation \
                    -Wno-error=declaration-after-statement \
                    -Wno-error=incompatible-pointer-types \
                    -Wno-error \
"

do_install() {
	install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
	install -d ${D}${sysconfdir}/Wireless/mt7610uSTA
	install -m 0644 ${S}/os/linux/mt7650u_sta.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/mt7650u_sta.ko
	install -m 0644 ${S}/RT2870STA.dat ${D}${sysconfdir}/Wireless/mt7610uSTA/mt7610uSTA.dat
	install -m 0644 ${S}/RT2870STACard.dat ${D}${sysconfdir}/Wireless/mt7610uSTA/mt7610uSTACard.dat
	install -m 0644 ${S}/conf/SingleSKU.dat ${D}${sysconfdir}/Wireless/mt7610uSTA/SingleSKU.dat
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI[md5sum] = "2b552aff1bbd4effe94185e222eb761e"
SRC_URI[sha256sum] = "c0061b9010b80c1fc09d78786317957044bde43e2a127ecefd66d4faa12d2906"

FILES:${PN} += "${sysconfdir}/Wireless/mt7610uSTA/mt7610uSTACard.dat ${sysconfdir}/Wireless/mt7610uSTA/mt7610uSTA.dat ${sysconfdir}/Wireless/mt7610uSTA/SingleSKU.dat"

