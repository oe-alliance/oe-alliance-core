DESCRIPTION = "Firewall"

require conf/license/openpli-gplv2.inc

DEPENDS = "iptables virtual/kernel"
RDEPENDS_${PN} = "iptables kernel-module-ip-tables kernel-module-ip-conntrack kernel-module-ipt-reject kernel-module-ipt-state kernel-module-iptable-filter"

SRC_URI = "file://firewall.sh file://firewall.users"

PV = "1.0"
PR = "r1"

S = "${WORKDIR}"

INITSCRIPT_NAME = "firewall"
INITSCRIPT_PARAMS = "defaults"

inherit update-rc.d

do_install() {
	install -d ${D}/etc/init.d
	install -m 0755 ${WORKDIR}/firewall.sh ${D}/etc/init.d/firewall
	install -d ${D}/etc
	install -m 0755 ${WORKDIR}/firewall.users ${D}/etc/firewall.users
}
