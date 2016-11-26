SUMMARY = "AML remote setup"
LICENSE = "GPLv2"
SECTION = "base"
PRIORITY = "required"

PR = "r8"

require conf/license/license-gplv2.inc

SRCREV = "643104015fcca9882a73ece983025d52205fdc29"

SRC_URI = "git://github.com/codesnake/amremote.git \
           file://wetek.conf \
           file://wetek1.conf \
           file://wetek2.conf \
           file://wetek3.conf \
           file://wetek_play2.conf \
           file://alien.conf \
           file://alien2.conf \
           file://octagonsf8.conf \
           file://wetek_et10000remote.conf \
           file://wetek_hd2400remote.conf \
           file://wetek_tmnanoremote.conf \
           file://gb800ueplus.conf \
           file://gilx3.conf \
           file://zgemmastar.conf \
"

S = "${WORKDIR}/git"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -d ${D}${sysconfdir}/amremote
    install -m 0755 ${S}/remotecfg ${D}${bindir}/
    if [ "${MACHINE}" = "wetekplay2" ]; then
	install -m 0644 ${WORKDIR}/wetek_play2.conf ${D}${sysconfdir}/amremote/wetek.conf
    else
    	install -m 0644 ${WORKDIR}/wetek.conf ${D}${sysconfdir}/amremote/
    fi
    install -m 0644 ${WORKDIR}/wetek1.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/wetek2.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/wetek3.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/wetek_play2.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/alien.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/alien2.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/octagonsf8.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/wetek_et10000remote.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/wetek_hd2400remote.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/wetek_tmnanoremote.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/gilx3.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/gb800ueplus.conf ${D}${sysconfdir}/amremote/
    install -m 0644 ${WORKDIR}/zgemmastar.conf ${D}${sysconfdir}/amremote/
}


FILES_${PN} = "${bindir} ${sysconfdir}"

