SUMMARY = "mediatek 7601u"
inherit oea-wifi-driver

# wifi_driver() picks the driver from this machine's kernel, so this is machine specific
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
    ${@wifi_driver(d, 'mt7601u', 'kernel-module-mt7601u', 'mt7601u')} \
    firmware-mt7601u \
    "

PV = "1.0"
PR = "r3"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
