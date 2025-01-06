FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${DISTRO_NAME}:"

PR .= ".24"

SRC_URI += " \
    file://mount.sh \
    file://automount.rules \
    file://localextra.rules \
    file://write-deviceinfo.rules \
    file://device-info.sh \
"

do_install:append() {
    if [ -n "${MTD_BLACK}" ]; then
        echo "/dev/${MTD_BLACK}*" >> "${D}${sysconfdir}/udev/mount.ignorelist"
    fi
    install -m 0644 ${S}/write-deviceinfo.rules    ${D}${sysconfdir}/udev/rules.d/write-deviceinfo.rules
    install -m 0755 ${S}/device-info.sh ${D}${sysconfdir}/udev/scripts/device-info.sh
}