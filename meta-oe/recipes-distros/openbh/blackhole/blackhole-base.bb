DESCRIPTION = "OpenBh extra files"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "OpenBh Team"

require conf/license/license-gplv2.inc

SRC_URI = "file://Ncam_Ci.sh file://StartBhCam file://Delete_all_Crashlogs.sh file://Ifconfig.sh \
	file://openvpn.log file://Netstat.sh file://Uptime.sh file://bh_swap file://skin_plugins.xml \
	file://*.png"

PR = "r17"

FILES:${PN} = "/"

do_compile() {
	echo "${MACHINE}" > ${WORKDIR}/bhmachine
	echo "${DISTRO_NAME} ${DISTRO_VERSION}" > ${WORKDIR}/bhversion
}


do_install() {

	mkdir -p ${D}/usr/camscript
	mkdir -p ${D}/usr/script
	mkdir -p ${D}/usr/uninstall
	mkdir -p ${D}/usr/share/enigma2/skin_plugins
	mkdir -p ${D}/usr/share/enigma2/skin_plugins/buttons

	install -d ${D}/etc
	install -m 0644 ${WORKDIR}/bhmachine ${D}/etc/bhmachine
	install -m 0644 ${WORKDIR}/bhversion ${D}/etc/bhversion

	install -d ${D}/usr/share/enigma2
	install -m 0644 ${WORKDIR}/skin_plugins.xml ${D}/usr/share/enigma2/skin_plugins.xml
	install -m 0644 ${WORKDIR}/skin_plugins ${D}/usr/share/enigma2/skin_plugins/buttons
	for x in /*.png; do
		install -m 0644 ${WORKDIR}/$x ${D}/usr/share/enigma2/skin_plugins/buttons/$x
	done


	install -d ${D}/usr/bin
	for x in /StartBhCam; do
		install -m 0755 ${WORKDIR}/$x ${D}/usr/bin/$x
	done


	install -d ${D}/usr/camscript
	install -m 0755 ${WORKDIR}/Ncam_Ci.sh ${D}/usr/camscript/Ncam_Ci.sh

	install -d ${D}/usr/script
	for x in /Delete_all_Crashlogs.sh Ifconfig.sh Netstat.sh Uptime.sh; do
		install -m 0755 ${WORKDIR}/$x ${D}/usr/script/$x
	done

	install -d ${D}/etc/openvpn
	for x in /openvpn.log; do
		install -m 0644 ${WORKDIR}/$x ${D}/etc/openvpn/$x
	done

	install -d ${D}/etc/init.d
	install -m 0755 ${WORKDIR}/bh_swap ${D}/etc/init.d/bh_swap

	install -d ${D}/etc/rc3.d
	ln -sf /etc/init.d/bh_swap ${D}/etc/rc3.d/S40bh_swap

#	install -d ${D}/etc/rc4.d
#	ln -s /etc/init.d/openvpn ${D}/etc/rc4.d/S20openvpn

}
