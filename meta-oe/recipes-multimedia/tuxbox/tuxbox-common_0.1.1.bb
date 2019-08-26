SUMMARY = "Tuxbox common files"
LICENSE = "CLOSED"
MAINTAINER = "OE-Alliance team"
require conf/license/license-close.inc
inherit allarch

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

SRC_URI="git://github.com/oe-alliance/oe-alliance-tuxbox-common.git;protocol=git"

FILES_${PN} = "/"

S = "${WORKDIR}/git"

DVB-S_LISTS = "satellites.xml"
DVB-T_LISTS = "terrestrial.xml cables.xml atsc.xml"


do_install() {
    install -d ${D}${sysconfdir}/tuxbox/
    install -d ${D}${datadir}/tuxbox

    install -m 0644 ${S}/src/timezone.xml ${D}${sysconfdir}/tuxbox/timezone.xml
    ln -sf ${sysconfdir}/tuxbox/timezone.xml ${D}${sysconfdir}/

    ln -sf ${datadir} ${D}/share

    for i in ${DVB-T_LISTS} ${DVB-S_LISTS}; do
        install -m 0644 ${S}/src/$i ${D}${sysconfdir}/tuxbox/$i
        ln -sf ${sysconfdir}/tuxbox/$i ${D}${sysconfdir}/;
        ln -sf ${sysconfdir}/tuxbox/$i ${D}${datadir}/;
        ln -sf ${sysconfdir}/tuxbox/$i ${D}${datadir}/tuxbox/;
    done;
}
