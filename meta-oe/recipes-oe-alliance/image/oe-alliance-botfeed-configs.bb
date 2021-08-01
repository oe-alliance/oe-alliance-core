SUMMARY = "Configuration files for oe-Alliance-botfeeds"
PR = "r0"

require conf/license/license-gplv2.inc

FEEDURL = "https://raw.githubusercontent.com/oe-alliance"
FEEDS = "oe-alliance-settings-feed"

do_compile() {
    mkdir -p ${S}/${sysconfdir}/opkg
    for feed in ${FEEDS}; do
        echo "src/gz ${feed} ${FEEDURL}/${feed}/master/feed" > ${S}/${sysconfdir}/opkg/${feed}.conf
    done
}
do_install () {
        install -d ${D}${sysconfdir}/opkg
        install -m 0644 ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg/
}

CONFFILES_${PN} += '${@ " ".join( [ ( "${sysconfdir}/opkg/%s-feed.conf" % feed ) for feed in "${FEEDS}".split() ] ) }'
