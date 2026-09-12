FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/${DISTRO_NAME}:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
PR .= ".2"
PR:append:openatv = ".1"
PR:append:openspa = ".1"

PACKAGE_ARCH := "${MACHINE_ARCH}"

RDEPENDS:${PN}:append = "${@bb.utils.contains_any('DISTRO_NAME', 'openatv openspa', ' util-linux-flock', '', d)}"

SRC_URI += " \
    file://67_init_hddown.dpatch \
    file://92_sata-hddown.dpatch \
"

do_install:append() {
    rm ${D}${sysconfdir}/rc*.d/*bootlogd
}

