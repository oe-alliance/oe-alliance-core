SUMMARY = "VUPLUS satip client using vtuner"
LICENSE = "CLOSED"

SRCDATE = "20150623"
SRCDATE_PR = "r1"
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

SRC_URI[md5sum] = "6b4123717ab2c0f74b53761ddc5f058e"
SRC_URI[sha256sum] = "4bbd2446cd1cdb60546385db2938275b80e6c86eb03ca52dbeeedf1bd3b10cad"
