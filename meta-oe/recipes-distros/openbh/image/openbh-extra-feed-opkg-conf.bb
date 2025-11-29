SUMMARY = "OpenBh extra feed opkg conf"
PRIORITY = "required"
MAINTAINER = "OpenBh Team"

require conf/license/license-gplv2.inc
inherit allarch

PV = "${IMAGE_VERSION}"
PR = "r2"

PACKAGES = "${PN}"

S = "${UNPACKDIR}"

feed_name = "openbh-extra-feed"

do_compile() {
    mkdir -p ${S}/${sysconfdir}/opkg
    echo "src/gz ${feed_name} http://feeds.openbh.net/extra/5.6/" > ${S}/${sysconfdir}/opkg/${feed_name}.conf
}

do_install() {
    install -d ${D}${sysconfdir}/opkg
    install -m 0644 ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg
}