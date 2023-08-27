SUMMARY = "HDFRadio feed opkg conf"
PRIORITY = "required"
MAINTAINER = "HDFreaks"

require conf/license/license-gplv2.inc
inherit allarch

PV = "${IMAGE_VERSION}"
PR = "r1"

PACKAGES = "${PN}"

S = "${WORKDIR}"

feed_name = "hdfradio-feed"

do_compile() {
    mkdir -p ${S}/${sysconfdir}/opkg
if ${@bb.utils.contains('DISTRO_VERSION','7.3','true','false',d)}; then
    URI="https://feed.hdfreaks.cc/hdfradio/python3.11.2_hdf_7.3"
elif ${@bb.utils.contains('DISTRO_VERSION','7.2','true','false',d)}; then
    URI="https://feed.hdfreaks.cc/hdfradio/python3.11.0_hdf_7.2"
elif ${@bb.utils.contains('DISTRO_VERSION','7.0','true','false',d)}; then
    URI="https://feed.hdfreaks.cc/hdfradio/python3.9.7_hdf_7.0"
else
    URI="https://feed.hdfreaks.cc/hdfradio/python2_hdf_6.5"
fi
    echo "src/gz ${feed_name} ${URI}" > ${S}/${sysconfdir}/opkg/${feed_name}.conf
}

do_install() {
    install -d ${D}${sysconfdir}/opkg || true
    install -m 0644 ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg
}
