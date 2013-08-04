PV = "20110114"
PR = "r0"
DESCRIPTION = "Tune kernel parameters in /etc/sysconf"

require conf/license/openpli-gplv2.inc

PACKAGES = "${PN}"

SRC_URI = "file://sysctl.conf"

# Users may chose to edit or create their own
CONFFILES_${PN} = "/etc/sysctl.conf"

do_install() {
	install -d ${D}/etc
	install -m 0755 ${WORKDIR}/sysctl.conf ${D}/etc/sysctl.conf
}
