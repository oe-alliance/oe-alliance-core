FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI += " \
    file://67_init_hddown.dpatch \
    file://92_sata-hddown.dpatch \
    ${@base_contains("MACHINE_FEATURES", "gbprogress", "file://proc_progressgb.patch", "file://proc_progress.patch", d)} \
    ${@base_contains("MACHINE_FEATURES", "gbplusprogress", "file://proc_progressgbplus.patch", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "vuprogress", "file://proc_progress_vuplus.patch", "", d)}"

SRC_URI_append_sh4 = " \
    file://devinit \
    "

do_install_append() {
    rm ${D}${sysconfdir}/rc*.d/*bootlogd
}

do_install_append_sh4() {
    install -d ${D}/bin
    install -m 755 ${WORKDIR}/devinit ${D}/bin
}

do_install_append_spark7162() {
    # AOTOM rtc needs to be in localtime or standby time display will be wrong.
    sed -i -e '/^UTC=yes/{
s/^/# /;
a# *** aotom RTC on SPARK needs hwclock in localtime ***
aUTC=no
}' ${D}${sysconfdir}/default/rcS
}

FILES_${PN}_append_sh4 = " \
    /bin \
"
