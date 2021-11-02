SUMMARY = "Tuxbox common files"
LICENSE = "CLOSED"
MAINTAINER = "OE-Alliance team"
require conf/license/license-close.inc
inherit allarch

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

SRC_URI="git://github.com/oe-alliance/oe-alliance-tuxbox-common.git;protocol=https"

FILES_${PN} = "/"

S = "${WORKDIR}/git"

DVB-S_LISTS = "satellites.xml"
DVB-T_LISTS = "terrestrial.xml cables.xml atsc.xml"


do_install() {
    install -d ${D}/etc/tuxbox/
    install -d ${D}/usr/share/tuxbox

    install -m 0644 ${S}/src/timezone.xml ${D}/etc/tuxbox/timezone.xml
    ln -sf /etc/tuxbox/timezone.xml ${D}/etc/

    ln -sf /usr/share ${D}/share

    for i in ${DVB-T_LISTS} ${DVB-S_LISTS}; do
        install -m 0644 ${S}/src/$i ${D}/etc/tuxbox/$i
        ln -sf /etc/tuxbox/$i ${D}/etc/;
        ln -sf /etc/tuxbox/$i ${D}/usr/share/;
        ln -sf /etc/tuxbox/$i ${D}/usr/share/tuxbox/;
    done;
}
