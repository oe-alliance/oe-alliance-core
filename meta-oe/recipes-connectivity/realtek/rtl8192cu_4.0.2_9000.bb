SUMMARY = "Driver for Realtek USB wireless devices"
HOMEPAGE = "http://www.realtek.com/"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://os_dep/linux/os_intfs.c;endline=19;md5=72c75de415f1e8a42587d170459677e2"

SRC_URI = " \
    file://rtl8188C_8192C_usb_linux_v4.0.2_9000.20130911.tar.gz \
    file://r8192cu_remove_debug.patch \
    file://rt8192cu-strncasecmp.patch \
    file://rtl8192cu-makefile.patch \
    file://rtl8192cu-gcc5.patch \
    file://rtl8192cu-fix_ndo_select_queue.patch \
    file://CHECKSM_IPV6_H.patch \
    "

S = "${WORKDIR}/rtl8188C_8192C_usb_linux_v4.0.2_9000.20130911"

inherit module siteinfo

EXTRA_OEMAKE = "CONFIG_RTL8192CU=m KDIR=${STAGING_KERNEL_DIR}"

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
                    -Wno-error=return-mismatch \
                    -Wno-error \
"

do_configure() {
        sed -e "s/^CONFIG_PLATFORM_I386_PC.*=.*y/EXTRA_CFLAGS += -DCONFIG_${@oe.utils.conditional('SITEINFO_ENDIANNESS', 'le', 'LITTLE', 'BIG', d)}_ENDIAN/" -i Makefile
}
do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
        oe_runmake -C "${STAGING_KERNEL_DIR}" M="${S}" modules 
}

do_install() {
        install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
        install -m 0644 ${S}/8192cu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}
