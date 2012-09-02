MAINTAINER = "oe-alliance"

PRINC = "13"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	file://cables.xml \
	file://terrestrial.xml \
	file://satellites.xml \
	"

DVB-S_LISTS = "satellites.xml"
DVB-T_LISTS = "terrestrial.xml cables.xml"

do_install_append() {
	for i in ${DVB-T_LISTS} ${DVB-S_LISTS}; do
		install -m 0644 ${S}/$i ${D}/etc/tuxbox/$i
		ln -sf /etc/tuxbox/$i ${D}/etc/;
		ln -sf /etc/tuxbox/$i ${D}/usr/share/;
		ln -sf /etc/tuxbox/$i ${D}/usr/share/tuxbox/;
	done;

	rm -f ${D}/etc/tuxbox/scart.conf
}
