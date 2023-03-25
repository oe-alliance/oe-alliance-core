SUMMARY = "oe-alliance picon feed opkg conf"
PRIORITY = "required"
MAINTAINER = "oe-alliance team"

require conf/license/license-gplv2.inc
inherit allarch

PV = "1.0"
PR = "r0"

PACKAGES = "${PN}"

S = "${WORKDIR}"

feed_name = "oe-alliance-picon-feed"

do_compile() {
    mkdir -p ${S}${sysconfdir}/opkg
    echo "src/gz ${feed_name} https://raw.githubusercontent.com/oe-alliance/picons-feed/gh-pages" > ${S}${sysconfdir}/opkg/${feed_name}.conf
}

do_install() {
    install -d ${D}${sysconfdir}/opkg
    install -m 0644 ${S}${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg
}

pkg_postinst:openatv:${PN} () {
    #!/bin/sh
    rm -f /etc/opkg/openvix-picon-feed.conf
    exit 0
}

CONFFILES:${PN} += "${sysconfdir}/opkg/${feed_name}.conf"
