FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= ".3"

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI += " \
    file://67_init_hddown.dpatch \
    file://92_sata-hddown.dpatch \
"

do_install_append() {
    rm ${D}${sysconfdir}/rc*.d/*bootlogd
}

do_install_append_spark7162() {
    # AOTOM rtc needs to be in localtime or standby time display will be wrong.
    sed -i -e '/^UTC=yes/{
s/^/# /;
a# *** aotom RTC on SPARK needs hwclock in localtime ***
aUTC=no
}' ${D}${sysconfdir}/default/rcS
}
