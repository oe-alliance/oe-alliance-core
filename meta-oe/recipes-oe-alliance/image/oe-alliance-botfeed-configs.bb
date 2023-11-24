SUMMARY = "Configuration files for oe-Alliance-botfeeds"
PR = "r1"

require conf/license/license-gplv2.inc

FEEDURL = "https://raw.githubusercontent.com/oe-alliance"
FEEDS = "enigma2-settings-feed"

do_compile() {
    mkdir -p ${S}/${sysconfdir}/opkg
    for feed in ${FEEDS}; do
        echo "src/gz ${feed} ${FEEDURL}/${feed}/gh-pages" > ${S}/${sysconfdir}/opkg/${feed}.conf
    done
}
do_install () {
        install -d ${D}${sysconfdir}/opkg
        install -m 0644 ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg/
}

CONFFILES:${PN} += '${@ " ".join( [ ( "${sysconfdir}/opkg/%s-feed.conf" % feed ) for feed in "${FEEDS}".split() ] ) }'

pkg_preinst:${PN} () {
#!/bin/sh
if [ -f /etc/opkg/oe-alliance-settings-feed.conf ]; then
    rm -rf /etc/opkg/oe-alliance-settings-feed.conf
fi
exit 0
}