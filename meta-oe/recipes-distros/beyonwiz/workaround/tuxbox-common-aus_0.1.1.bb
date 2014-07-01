SUMMARY = "Tuxbox common files"
LICENSE = "CLOSED"
MAINTAINER = "Beyonwiz team"

inherit gitpkgv allarch
PV = "1"
PR = "r4"

SRC_URI = "file://satellites.xml file://terrestrial.xml file://cables.xml file://timezone.xml"

FILES_${PN} = "/"

S = "${WORKDIR}/"

DVB-S_LISTS = "satellites.xml"
DVB-T_LISTS = "terrestrial.xml cables.xml"


do_install() {
    install -d ${D}/etc/tuxbox/
    install -d ${D}/usr/share/tuxbox

    install -m 0644 ${S}/timezone.xml ${D}/etc/tuxbox/timezone.xml
    ln -sf /etc/tuxbox/timezone.xml ${D}/etc/

    ln -sf /usr/share ${D}/share

    for i in ${DVB-T_LISTS} ${DVB-S_LISTS}; do
        install -m 0644 ${S}/$i ${D}/etc/tuxbox/$i
        ln -sf /etc/tuxbox/$i ${D}/etc/;
        ln -sf /etc/tuxbox/$i ${D}/usr/share/;
        ln -sf /etc/tuxbox/$i ${D}/usr/share/tuxbox/;
    done;
}
