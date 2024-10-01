DESCRIPTION = "OpenBh extra files"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "OpenBh Team"

require conf/license/license-gplv2.inc

SRC_URI = "file://Ncam_Ci.sh file://StartBhCam file://Delete_all_Crashlogs.sh file://Ifconfig.sh \
	file://openvpn.log file://Netstat.sh file://Uptime.sh file://bh_swap file://skin_plugins.xml"

PR = "r21"

FILES:${PN} = "/"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_compile() {
    echo "${MACHINE}" > ${S}/bhmachine
    echo "${DISTRO_NAME} ${DISTRO_VERSION}" > ${S}/bhversion
}

do_install() {

	mkdir -p ${D}/usr/camscript
	mkdir -p ${D}/usr/script
	mkdir -p ${D}/usr/uninstall

	install -d ${D}/etc
	install -m 0644 ${S}/bhmachine ${D}/etc/bhmachine
	install -m 0644 ${S}/bhversion ${D}/etc/bhversion

	install -d ${D}/usr/share/enigma2
	install -m 0644 ${S}/skin_plugins.xml ${D}/usr/share/enigma2/skin_plugins.xml


	install -d ${D}/usr/bin
	for x in /StartBhCam; do
		install -m 0755 ${S}/$x ${D}/usr/bin/$x
	done


	install -d ${D}/usr/camscript
	install -m 0755 ${S}/Ncam_Ci.sh ${D}/usr/camscript/Ncam_Ci.sh

	install -d ${D}/usr/script
	for x in /Delete_all_Crashlogs.sh Ifconfig.sh Netstat.sh Uptime.sh; do
		install -m 0755 ${S}/$x ${D}/usr/script/$x
	done

	install -d ${D}/etc/openvpn
	for x in /openvpn.log; do
		install -m 0644 ${S}/$x ${D}/etc/openvpn/$x
	done

	install -d ${D}/etc/init.d
	install -m 0755 ${S}/bh_swap ${D}/etc/init.d/bh_swap

	install -d ${D}/etc/rc3.d
	ln -sf /etc/init.d/bh_swap ${D}/etc/rc3.d/S40bh_swap

#	install -d ${D}/etc/rc4.d
#	ln -s /etc/init.d/openvpn ${D}/etc/rc4.d/S20openvpn

}
