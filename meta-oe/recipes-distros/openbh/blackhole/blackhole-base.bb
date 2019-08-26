DESCRIPTION = "Open BlackHole extra files"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "Black Hole team"

require conf/license/license-gplv2.inc

SRC_URI = "file://Ncam_Ci.sh file://StartBhCam file://Delete_all_Crashlogs.sh file://Ifconfig.sh \
	file://openvpn.log file://Netstat.sh file://Uptime.sh file://bp_swap"

PR = "r10"

FILES_${PN} = "/"

do_compile() {
	echo "${MACHINE}" > ${WORKDIR}/bpmachine
	echo "${DISTRO_NAME} ${DISTRO_VERSION}" > ${WORKDIR}/bpversion
}


do_install() {

	mkdir -p ${D}/usr/camscript
	mkdir -p ${D}/usr/script
	mkdir -p ${D}/usr/uninstall

	install -d ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/bpmachine ${D}${sysconfdir}/bpmachine
	install -m 0644 ${WORKDIR}/bpversion ${D}${sysconfdir}/bpversion
	

	install -d ${D}${bindir}
	for x in /StartBhCam; do
		install -m 0755 ${WORKDIR}/$x ${D}${bindir}/$x
	done
	

	install -d ${D}/usr/camscript
	install -m 0755 ${WORKDIR}/Ncam_Ci.sh ${D}/usr/camscript/Ncam_Ci.sh
	
	install -d ${D}/usr/script
	for x in /Delete_all_Crashlogs.sh Ifconfig.sh Netstat.sh Uptime.sh; do
		install -m 0755 ${WORKDIR}/$x ${D}/usr/script/$x
	done
	
	install -d ${D}${sysconfdir}/openvpn
	for x in /openvpn.log; do
		install -m 0644 ${WORKDIR}/$x ${D}${sysconfdir}/openvpn/$x
	done
	
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/bp_swap ${D}${sysconfdir}/init.d/bp_swap
	
	install -d ${D}${sysconfdir}/rc3.d
#	ln -sf ${sysconfdir}/init.d/openvpn ${D}${sysconfdir}/rc3.d/S20openvpn
	ln -sf ${sysconfdir}/init.d/bp_swap ${D}${sysconfdir}/rc3.d/S40bp_swap

	install -d ${D}${sysconfdir}/rc4.d
	ln -s ${sysconfdir}/init.d/openvpn ${D}${sysconfdir}/rc4.d/S20openvpn

}
