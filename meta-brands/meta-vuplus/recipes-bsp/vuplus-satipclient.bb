SUMMARY = "VUPLUS satip client using vtuner"
LICENSE = "CLOSED"

SRCDATE = "20150629"
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

SRC_URI[md5sum] = "b907964a587c24e53c4a11486df36c1a"
SRC_URI[sha256sum] = "52afeb599a857e9db038f4c3cb84efd2093e6f62a29ade2e9ae5b32cdc67cc72"
