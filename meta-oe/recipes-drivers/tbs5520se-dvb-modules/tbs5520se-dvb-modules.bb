SUMMARY = "TBS 5520SE DVB USB kernel modules"
DESCRIPTION = "Out-of-tree kernel modules for the TurboSight TBS 5520SE \
multi-standard USB DVB tuner. Provides DVB-S/S2/S2X (satellite) via the \
AV2018 tuner and DVB-T/T2/C/C2/ISDB-T (terrestrial/cable) via the Si2157 \
tuner, with the Si2183 multi-standard demodulator. \
The satellite frontend is self-registered, so no dvb-usb framework \
patches are required."
HOMEPAGE = "https://github.com/tbsdtv/linux_media"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "virtual/kernel"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

SRC_URI = " \
    file://Makefile \
    file://tbs5520se.c \
    file://tbs5520se.h \
    file://si2183.c \
    file://si2183.h \
    file://av201x.c \
    file://av201x.h \
    file://av201x_priv.h \
    file://compat.h \
"

PV = "1.0"
PR = "r0"

S = "${WORKDIR}"

inherit module

EXTRA_OEMAKE += "KSRC=${STAGING_KERNEL_DIR}"

do_compile() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    oe_runmake -C ${STAGING_KERNEL_BUILDDIR} M=${S} modules
}

do_install() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    oe_runmake -C ${STAGING_KERNEL_BUILDDIR} M=${S} \
        DEPMOD=echo MODLIB="${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}" \
        INSTALL_MOD_PATH="${D}" \
        modules_install
}

COMPATIBLE_MACHINE = ".*"
