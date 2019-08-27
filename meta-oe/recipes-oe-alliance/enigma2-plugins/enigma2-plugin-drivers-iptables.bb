SUMMARY = "linux iptables firewall"

require conf/license/license-gplv2.inc

DEPENDS = "iptables virtual/kernel"
RRECOMMENDS_${PN} = "\
 iptables \
 kernel-module-nf-nat-masquerade-ipv4 \
 kernel-module-nf-nat-ipv4 \
 kernel-module-nf-nat \
 kernel-module-nf-log-ipv4 \
 kernel-module-nf-log-common \
 kernel-module-iptable-mangle \
 kernel-module-ipt-reject \
 kernel-module-ipt-ecn \
 kernel-module-ipt-ah \
 kernel-module-xt-connmark \
 kernel-module-xt-connlimit \
 kernel-module-xt-connbytes \
 kernel-module-nf-conntrack-ipv4 \
 kernel-module-iptable-filter \
 kernel-module-nf_conntrack \
 kernel-module-ip-tables \
 kernel-module-ip-conntrack \
 kernel-module-nf-reject-ipv4 \
 kernel-module-nf-defrag-ipv4 \
 kernel-module-ipt-reject \
 kernel-module-ipt-state \
 "

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
    install -d ${D}/${sysconfdir}/modules-load.d
    echo nf_nat_masquerade_ipv4 >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo nf_nat_ipv4 >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo nf_nat >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo nf_log_ipv4 >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo nf_log_common >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo iptable_mangle >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo ipt_REJECT >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo ipt_ECN >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo ipt_ah >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo xt_connmark >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo xt_connlimit >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo xt_connbytes >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo xt_comment >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo nf_conntrack_ipv4 >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo iptable_filter >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo xt_conntrack >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo nf_conntrack >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo ip_tables >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo x_tables >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo nf_reject_ipv4 >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
    echo nf_defrag_ipv4 >> ${D}/${sysconfdir}/modules-load.d/firewall.conf
}

FILES_${PN} += "${sysconfdir}/modules-load.d/firewall.conf"
