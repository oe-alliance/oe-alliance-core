SUMMARY = "VUPLUS satip client using vtuner"
LICENSE = "CLOSED"

SRCDATE = "20150702"
SRCDATE_PR = "r0"
PR = "${SRCDATE}.${SRCDATE_PR}"

SRC_URI = " \
    http://code.vuplus.com/download/build.fc3abf29fb03f797e78f907928125638/embedded/satipclient/satipclient_${SRCDATE}_${SRCDATE_PR}.tar.gz \
    file://satipclient.sh \
"

S = "${WORKDIR}"

inherit update-rc.d

INITSCRIPT_NAME = "satipclient"
INITSCRIPT_PARAMS = "defaults"

do_install_append() {
    install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/satipclient ${D}/usr/bin
    install -d ${D}/etc/init.d
    install -m 0755 ${WORKDIR}/satipclient.sh ${D}/etc/init.d/satipclient
}

SRC_URI[md5sum] = "2609bf7f92dc8e807841b1af25b94c1d"
SRC_URI[sha256sum] = "401257a051ae9106504f0cfb771aa572db1512926ef038dcd25b45b2b154c955"
