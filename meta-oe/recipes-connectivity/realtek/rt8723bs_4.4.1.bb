SUMMARY = "Driver for Realtek 8723BS wireless/bluetooth devices"
HOMEPAGE = "http://www.realtek.com/"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
require conf/license/license-gplv2.inc

PR = "r3"

SRCREV = "7708383b086b31703d5b50b8ffa45991531cbe1c"

SRC_URI = "git://github.com/anthonywong/rtl8723bs.git;protocol=https;branch=master \
    file://0001-makefile-disable-POWER_SAVING.patch \
    file://rt8723bs-makefile.patch \
    file://rt8723bs-remove-debug.patch \
    file://rt8723bs-gcc5.patch \
    file://rt8723bs-add-4.8-support.patch \
    file://rt8723bs-add-4.11-support.patch \
    file://rt8723bs-add-4.12-support.patch \
    file://0001-add-kernel-4.15-support.patch \
    file://compat.patch \
    file://rt8723bs-add-4.19-support.patch \
    file://rt8723bs-add-4.20-support.patch \
    file://rt8723bs-add-5.0-support.patch \
    file://rt8723bs-add-5.1-support.patch \
    file://rt8723bs-add-5.2-support.patch \
    file://rt8723bs-add-5.6-support.patch \
    file://rt8723bs-add-5.8-support.patch \
    file://add-5.15-support.patch \
    "

S = "${WORKDIR}/git"

inherit module

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

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/staging/rtl8723bs
    install -m 0644 ${S}/r8723bs.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/staging/rtl8723bs
}

python do_package:prepend() {
    d.appendVar('PKGV', '-')
    d.appendVar('PKGV', d.getVar("KERNEL_VERSION", True).split("-")[0])
}
