SUMMARY = "OpenBh extra feed opkg conf"
PRIORITY = "required"
MAINTAINER = "OpenBh Team"

require conf/license/license-gplv2.inc
inherit allarch

PV = "${IMAGE_VERSION}"
PR = "r1"

PACKAGES = "${PN}"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

feed_name = "openbh-extra-feed"

do_compile() {
    mkdir -p ${S}/${sysconfdir}/opkg
    echo "src/gz ${feed_name} http://feeds.openbh.net/extra/" > ${S}/${sysconfdir}/opkg/${feed_name}.conf
}

do_install() {
    install -d ${D}${sysconfdir}/opkg
    install -m 0644 ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg
}