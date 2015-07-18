SUMMARY = "VUPLUS satip client using vtuner"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2c1c00f9d3ed9e24fa69b932b7e7aff2"

SRCDATE = "20150706"
SRCDATE_PR = "r0"
PR = "${SRCDATE}.${SRCDATE_PR}"

SRC_REV = ""

SRC_URI = " \
    git://github.com/vu-plus/satipclient.git;tag=${SRC_REV} \
    file://satipclient.sh \
"

S = "${WORKDIR}/git"

inherit autotools update-rc.d

INITSCRIPT_NAME = "satipclient"
INITSCRIPT_PARAMS = "defaults"

do_install_append() {
    install -d ${D}/etc/init.d
    install -m 0755 ${WORKDIR}/satipclient.sh ${D}/etc/init.d/satipclient
}
