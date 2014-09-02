PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI_append_dm800 = " file://tc_ematch-header-files.patch \
                         file://nl80211-header.patch \
                         file://stubs-o32_hard.h \
                         "

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}_${PV}:"

do_install_append_dm800() {
    install -m 0755 -d ${D}${includedir}/linux
    install -m 0755 -d ${D}${includedir}/linux/tc_ematch
    install -m 0644 ${S}/include/linux/nl80211.h ${D}${includedir}/linux/nl80211.h
    install -m 0644 ${S}/include/linux/tc_ematch/tc_em_cmp.h   ${D}${includedir}/linux/tc_ematch/tc_em_cmp.h
    install -m 0644 ${S}/include/linux/tc_ematch/tc_em_nbyte.h ${D}${includedir}/linux/tc_ematch/tc_em_nbyte.h
    install -m 0644 ${S}/include/linux/tc_ematch/tc_em_text.h  ${D}${includedir}/linux/tc_ematch/tc_em_text.h
    install -m 0755 -d ${D}${includedir}/gnu
    install -m 0644 ${S}/stubs-o32_hard.h  ${D}${includedir}/gnu/stubs-o32_hard.h
}

FILES_${PN}-dev_append_dm800 = " ${includedir}/linux/tc_ematch/*.h ${includedir}/linux/nl80211.h"
