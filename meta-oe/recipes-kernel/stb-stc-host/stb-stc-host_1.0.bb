SUMMARY = "Broadcom Nexus host-STC bridge for STB Kodi"
DESCRIPTION = "Exposes the closed Broadcom DVB decoder STC to userspace so Kodi can synchronize hardware video with its audio master clock."
SECTION = "kernel/modules"

PR = "r3"

require conf/license/license-gplv2.inc

DEPENDS = "virtual/kernel"

SRC_URI = "file://gb_stc_host.c \
           file://Makefile \
"

S = "${UNPACKDIR}"

inherit module

do_configure[noexec] = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
# This package is pulled in only by BCM STB player builds. Runtime symbol
# detection keeps one bridge usable across the different vendor kernels.
COMPATIBLE_MACHINE = ".*"

do_compile() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    oe_runmake -C "${STAGING_KERNEL_BUILDDIR}" M="${S}" modules
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/media/stb
    install -m 0644 ${S}/gb_stc_host.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/media/stb/
    install -d ${D}${sysconfdir}/modules-load.d
    echo gb_stc_host > ${D}${sysconfdir}/modules-load.d/stb-stc-host.conf
}

FILES:${PN} += "${sysconfdir}/modules-load.d/stb-stc-host.conf"

pkg_postinst_ontarget:${PN}() {
    modprobe gb_stc_host >/dev/null 2>&1 || true
}
