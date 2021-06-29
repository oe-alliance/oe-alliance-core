SUMMARY = "mediatek 7601u"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    ${@bb.utils.contains("LINUX_WIFI_mt7106u", "kernel-module-mt7601u", "kernel-module-mt7601u", "mt7601u", d)} \
    firmware-mt7601u \
    "

LINUX_WIFI_mt7106u = " \
    ${@bb.utils.contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "sf8008", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "sf8008m", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "gbmv200", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "ustym4kpro", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "h9", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "h9se", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "h8", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "hzero", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "h9combo", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "h9combose", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "h10", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "h11", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "i55plus", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "i55se", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "hd60", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "hd61", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "multibox", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "multiboxse", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "pulse4k", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "pulse4kmini", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "viper4k", "kernel-module-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "dagsmv200", "kernel-module-mt7601u", "", d)} \
    "

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
