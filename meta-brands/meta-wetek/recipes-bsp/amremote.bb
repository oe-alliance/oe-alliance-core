SUMMARY = "AML remote setup"
LICENSE = "GPLv2"
SECTION = "base"
PRIORITY = "required"

PR = "r3"

require conf/license/license-gplv2.inc

SRCREV = "2bb64e38fc1f082af6c6a6bd24e58e9f0fcca010"

SRC_URI = "git://github.com/wetek-enigma/amremote.git \
           file://wetek.conf \
           file://wetek1.conf \
           file://wetek2.conf \
"

S = "${WORKDIR}/git"



do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -d ${D}${sysconfdir}/amremote
    install -m 0755 ${S}/remotecfg ${D}${bindir}/
    install -m 0644 ${WORKDIR}/wetek.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/wetek1.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/wetek2.conf ${D}${sysconfdir}/amremote/
}

FILES_${PN} = "${bindir} ${sysconfdir}"

