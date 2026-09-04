SUMMARY = "Driver for Ralink RT8070/3070/3370/5370/5372 USB 802.11abgn WiFi sticks"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://os/linux/rt_linux.c;endline=25;md5=21ed2a5918a3062a6c0323ef549f0803"

inherit module

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance-drivers/rt3070.git;protocol=https;branch=master;destsuffix=s"

S = "${UNPACKDIR}/s"
inherit module

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

# need only for dreambox linux-meson64 4.9
export KCFLAGS += " -std=gnu17 \
                    -Wno-error=misleading-indentation \
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
    install -m 0644 ${S}/*sta${KERNEL_OBJECT_SUFFIX} ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -d ${D}${sysconfdir}/Wireless/RT2870STA
    install -m 0644 ${S}/RT2870STA.dat ${D}${sysconfdir}/Wireless/RT2870STA
}

FILES:${PN}:append = "${sysconfdir}/Wireless"
