DESCRIPTION = "STLIRC is a package for managing infrared on SH4-based boxes."
SECTION = "base"
PRIORITY = "optional"
HOMEPAGE = "http://www.lirc.org"
LICENSE = "GPLv2"
DEPENDS = "virtual/kernel libusb1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RCONFLICTS_${PN} = "lirc"
RREPLACES_${PN} = "lirc"

PR = "r7"

SRC_URI = "http://sourceforge.mirrorservice.org/l/li/lirc/LIRC/0.9.0/lirc-${PV}.tar.bz2 \
    file://lirc-0.9.0-neutrino-uinput-hack.diff;patch=1 \
    file://lirc-0.9.0-try_first_last_remote.diff;patch=1 \
    file://lirc-0.9.0-uinput-repeat-fix.diff;patch=1 \
    file://fix-libusb-config.patch;patch=1 \
    file://lirc-0.9.0-repeat_and_delay_hack.patch \
    file://lirc-0.9.0-rename_input_device.patch \
    file://lircd.init \
    file://lircd_amiko8900.conf \
    file://lircd_amikoalien.conf \
    file://lircd_spark.conf \
"
SRC_URI[md5sum] = "b232aef26f23fe33ea8305d276637086"
SRC_URI[sha256sum] = "6323afae6ad498d4369675f77ec3dbb680fe661bea586aa296e67f2e2daba4ff"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3"

S = "${WORKDIR}/lirc-${PV}"

PARALLEL_MAKE = ""

CFLAGS_append = " -DUINPUT_NEUTRINO_HACK "

EXTRA_OECONF += "--with-kerneldir=${STAGING_KERNEL_BUILDDIR} ${DRIVER} --without-x --with-driver=none --with-driver=userspace "

inherit autotools module-base update-rc.d

INITSCRIPT_PACKAGES = "stlirc stlirc-exec"
INITSCRIPT_NAME = "lircd"
INITSCRIPT_PARAMS = "defaults 20"

EXTRA_OEMAKE = 'SUBDIRS="daemons tools"'

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install ${WORKDIR}/lircd.init ${D}${sysconfdir}/init.d/lircd
    rm -rf ${D}/var
    rm -rf ${D}/usr/bin/
    rm -f ${D}/usr/sbin/lircmd
    if [ -e ${WORKDIR}/lircd_${MACHINEBUILD}.conf ]; then
       install -m 0644 ${WORKDIR}/lircd_${MACHINEBUILD}.conf ${D}${sysconfdir}/lircd.conf
    elif [ "${BRAND_OEM}" = "fulan" ]; then
       install -m 0644 ${WORKDIR}/lircd_spark.conf ${D}${sysconfdir}/lircd.conf
    fi
}

