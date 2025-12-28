SUMMARY = "fstrim cron job"
MAINTAINER = "OpenATV Team"
SECTION = "base"
inherit allarch

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

S = "${UNPACKDIR}"

SRC_URI = "\
    file://fstrim \
    file://fstrim-all \
"

do_install() {
    install -d ${D}${sysconfdir}/cron.hourly
    install -d ${D}/usr/sbin
    install -m 0755 ${S}/fstrim    ${D}${sysconfdir}/cron.hourly/fstrim
    install -m 0755 ${S}/fstrim-all   ${D}/usr/sbin/fstrim-all
}