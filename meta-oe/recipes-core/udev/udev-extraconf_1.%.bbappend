FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${DISTRO_NAME}:"

PR .= ".33"

SRC_URI += " \
    file://mount.sh \
    file://automount.rules \
    file://localextra.rules \
    file://write-deviceinfo.rules \
    file://device-info.sh \
    file://startup.sh \
    file://startup.rules \
    file://40-realtek-zerocd.rules \
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

# OpenVix, OpenBh:
# We only want udev to bring up interfaces marked as auto
# (If every distro want this an edit of the base network.sh script
# should be done, and this removed).
#
    if ${@bb.utils.contains_any('DISTRO_NAME','openvix openbh','true','false',d)}; then
# \ needs escaping even within ''
# and the + (for 1-or-more) needs quoting for "normal" regexes.
#
        sed -i 's/iface \\+$INTERFACE/auto \\+$INTERFACE/' ${D}${sysconfdir}/udev/scripts/network.sh
    fi
}
