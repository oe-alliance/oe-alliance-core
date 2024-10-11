SUMMARY = "HDFRadio feed opkg conf"
PRIORITY = "required"
MAINTAINER = "HDFreaks"

require conf/license/license-gplv2.inc
inherit allarch

PV = "${IMAGE_VERSION}"
PR = "r2"

PACKAGES = "${PN}"

feed_name = "hdfradio-feed"

BRANCH := "${@os.popen('git branch --remote --contains | sed "s|[[:space:]]*origin/||"').read().strip()}"

do_compile() {
    mkdir -p ${S}/${sysconfdir}/opkg

      case "${BRANCH}" in
        "5.5")
            URI="https://feed.hdfreaks.cc/hdfradio/python3.12_hdf_7.5"
            ;;
        "5.4")
            URI="https://feed.hdfreaks.cc/hdfradio/python3.12_hdf_7.4"
            ;;
        "5.3")
            URI="https://feed.hdfreaks.cc/hdfradio/python3.11.2_hdf_7.3"
            ;;
        "5.2")
            URI="https://feed.hdfreaks.cc/hdfradio/python3.11.0_hdf_7.2"
            ;;
        "5.0")
            URI="https://feed.hdfreaks.cc/hdfradio/python3.9.7_hdf_7.0"
            ;;
        *)
            URI="https://feed.hdfreaks.cc/hdfradio/python2_hdf_6.5"
            ;;
    esac

    echo "src/gz ${feed_name} ${URI}" > ${S}/${sysconfdir}/opkg/${feed_name}.conf
}

do_install() {
    install -d ${D}${sysconfdir}/opkg || true
    install -m 0644 ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg
}
