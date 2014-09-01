DESCRIPTION = "LIRC is a package that allows you to decode and send infra-red signals of many commonly used remote controls."
DESCRIPTION_append_stlirc = " This package contains the lirc daemon, libraries and tools."
DESCRIPTION_append_stlirc-exec = " This package contains a daemon that runs programs on IR signals."
DESCRIPTION_append_stlirc-remotes = " This package contains some config files for remotes."
SECTION = "console/network"
PRIORITY = "optional"
HOMEPAGE = "http://www.lirc.org"
LICENSE = "GPLv2"
DEPENDS = "virtual/kernel"
RDEPENDS_lirc-exec = "stlirc"
RRECOMMENDS_${PN} = "stlirc-exec kernel-module-uinput"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RCONFLICTS_${PN} = "lirc"
RCONFLICTS_stlirc-exec = "lirc-exec"
RCONFLICTS_stlirc-remotes = "lirc-remotes"
RREPLACES_${PN} = "lirc"
RREPLACES_stlirc-exec = "lirc-exec"
RREPLACES_stlirc-remotes = "lirc-remotes"

PR = "r2"

SRC_URI = "http://prdownloads.sourceforge.net/lirc/lirc-${PV}.tar.bz2 \
    file://lirc-0.9.0-neutrino-uinput-hack.diff;patch=1 \
    file://lirc-0.9.0-try_first_last_remote.diff;patch=1 \
    file://lirc-0.9.0-uinput-repeat-fix.diff;patch=1 \
    file://lirc-0.9.0-repeat_and_delay_hack.patch \
    file://lirc-0.9.0-rename_input_device.patch \
    file://lircd.init \
    file://lircmd.init \
    file://lircexec.init \
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

EXTRA_OECONF += "--with-kerneldir=${STAGING_KERNEL_DIR} ${DRIVER} --without-x --with-driver=userspace "

inherit autotools module-base update-rc.d

INITSCRIPT_PACKAGES = "stlirc stlirc-exec"
INITSCRIPT_NAME = "lircd"
INITSCRIPT_PARAMS = "defaults 20"
INITSCRIPT_NAME_stlirc-exec = "lircexec"
INITSCRIPT_PARAMS_stlirc-exec = "defaults 21"

EXTRA_OEMAKE = 'SUBDIRS="daemons tools"'

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install ${WORKDIR}/lircd.init ${D}${sysconfdir}/init.d/lircd
    install ${WORKDIR}/lircexec.init ${D}${sysconfdir}/init.d/lircexec
    install -d ${D}${datadir}/lirc/
    cp -pPR ${S}/remotes ${D}${datadir}/lirc/
    rm -rf ${D}/dev
    rm -rf  ${D}/bin/pronto2lirc
    if [ -e ${WORKDIR}/lircd_${MACHINEBUILD}.conf ]; then
       install -m 0644 ${WORKDIR}/lircd_${MACHINEBUILD}.conf ${D}${sysconfdir}/lircd.conf
    elif [ "${BRAND_OEM}" = "fulan" ]; then
       install -m 0644 ${WORKDIR}/lircd_spark.conf ${D}${sysconfdir}/lircd.conf
    fi
}

PACKAGES =+ "stlirc-exec stlirc-remotes"

FILES_${PN}-dbg += "${bindir}/.debug ${sbindir}/.debug"
FILES_${PN}-dev += "${libdir}/liblirc_client.so"
FILES_${PN} = "${bindir} ${sbindir} ${libdir}/lib*.so.* ${sysconfdir} ${exec_prefix}/var"
FILES_stlirc-exec = "${bindir}/irexec ${sysconfdir}/init.d/lircexec"
FILES_stlirc-remotes = "${datadir}/lirc/remotes"


