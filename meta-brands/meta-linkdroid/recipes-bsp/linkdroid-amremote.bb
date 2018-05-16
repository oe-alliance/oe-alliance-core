SUMMARY = "linkdroid remote setup Supports Multi Configuration"
LICENSE = "GPLv2"
SECTION = "base"
PRIORITY = "required"

PR = "r1"

require conf/license/license-gplv2.inc

SRC_URI = "file://remote_tool.tar.gz \
           file://alien.conf \
           file://alien2.conf \
           file://alien5.conf \
           file://amremote.init \
"

S = "${WORKDIR}/remote_tool"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -d ${D}${sysconfdir}/amremote
    install -m 0755 ${S}/remotecfg ${D}${bindir}/
    install -m 0644 ${WORKDIR}/alien5.conf ${D}${sysconfdir}/amremote/remote.conf
    install -m 0644 ${WORKDIR}/alien.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/alien2.conf ${D}${sysconfdir}/amremote/
}


FILES_${PN} = "${bindir} ${sysconfdir}"

