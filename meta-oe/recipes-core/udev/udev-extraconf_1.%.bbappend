FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${DISTRO_NAME}:"

PR .= ".38"
PR:append:openatv = ".2"
PR:append:openspa = ".1"

RDEPENDS:${PN}-autonet:append = "${@bb.utils.contains_any('DISTRO_NAME', 'openatv openspa', ' util-linux-flock', '', d)}"
SRC_URI:append:openatv = " file://network-async.sh"
SRC_URI:append:openspa = " file://network-async.sh"

SRC_URI += " \
    file://mount.sh \
    file://automount.rules \
    file://localextra.rules \
    file://write-deviceinfo.rules \
    file://device-info.sh \
    file://startup.sh \
    file://startup.rules \
    file://40-realtek-zerocd.rules \
    file://99-dab-rtlsdr.rules \
"

do_install:append() {
    if [ -n "${MTD_BLACK}" ]; then
        echo "/dev/${MTD_BLACK}*" >> "${D}${sysconfdir}/udev/mount.ignorelist"
    fi
    install -m 0644 ${S}/write-deviceinfo.rules    ${D}${sysconfdir}/udev/rules.d/write-deviceinfo.rules
    install -m 0755 ${S}/device-info.sh ${D}${sysconfdir}/udev/scripts/device-info.sh
    install -m 0644 ${S}/startup.rules    ${D}${sysconfdir}/udev/rules.d/startup.rules
    install -m 0755 ${S}/startup.sh ${D}${sysconfdir}/udev/scripts/startup.sh
    install -m 0644 ${S}/40-realtek-zerocd.rules   ${D}${sysconfdir}/udev/rules.d/40-realtek-zerocd.rules
    install -m 0644 ${S}/99-dab-rtlsdr.rules       ${D}${sysconfdir}/udev/rules.d/99-dab-rtlsdr.rules

    # These distros use parallel rc startup; retain synchronous policy elsewhere.
    if ${@bb.utils.contains_any('DISTRO_NAME','openatv openspa','true','false',d)}; then
        install -m 0755 ${S}/network-async.sh ${D}${sysconfdir}/udev/scripts/network.sh
    fi

    # Preserve OpenViX/OpenBH's policy in the original network helper.
    if ${@bb.utils.contains_any('DISTRO_NAME','openvix openbh','true','false',d)}; then
        sed -i 's/iface \\+$INTERFACE/auto \\+$INTERFACE/' ${D}${sysconfdir}/udev/scripts/network.sh
    fi
}
