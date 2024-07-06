SUMMARY = "Realtek 8723A v1.0"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://Makefile;md5=6325fabe3996c2783285cc021ee13c96"

inherit module


MACHINE_KERNEL_PR:append = ".0"

SRC_URI = "https://source.mynonpublic.com/ini/rtl8723A_WiFi_linux_v4.1.6_7336.20140624.tar.gz \
    file://rt8723a-gcc5.patch \
    file://0001-add-kernel-4.11-support.patch \
    file://0001-add-kernel-4.15-support.patch \
    file://0001-add-kernel-4.19-support.patch \
    file://0001-add-kernel-5.1-support.patch \
    file://0001-add-kernel-5.2-support.patch \
    file://0001-add-kernel-5.8-support.patch \
    file://add-5.15-support.patch \
    "

inherit module

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

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

S = "${WORKDIR}/rtl8723A_WiFi_linux_v4.1.6_7336.20140624"

do_compile () {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
    oe_runmake 'MODPATH={D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless' \
        'KERNEL_SOURCE=${STAGING_KERNEL_DIR}' \
        'LINUX_SRC=${STAGING_KERNEL_DIR}' \
        'KDIR=${STAGING_KERNEL_DIR}' \
        'KERNDIR=${STAGING_KERNEL_DIR}' \
        'KSRC=${STAGING_KERNEL_DIR}' \
        'KERNEL_VERSION=${KERNEL_VERSION}' \
        'KVER=${KERNEL_VERSION}' \
        'CC=${KERNEL_CC}' \
        'AR=${KERNEL_AR}' \
        'LD=${KERNEL_LD}'
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8723au.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless

}

SRC_URI[md5sum] = "922f8fb001ee8d58f87737453834e2b7"
SRC_URI[sha256sum] = "b6efcb3f2100065117ed910a7e1fbba1fec2b7b968441719b75c4a3f7b12a7e0"


