DESCRIPTION = "This package includes some useful diagnostics tools for \
IPv6 networks, including ndisc6, rdisc6, tcptraceroute6 and traceroute6."
SECTION = "net"
HOMEPAGE = "http://www.remlab.net/ndisc6/"
LICENSE = "GPL-2.0"

# The tcptraceroute6 and tracert6 commands depend on rltraceroute6 to
# perform the actual trace operation.
RDEPENDS_${PN}-tcptraceroute6 = "${PN}-rltraceroute6"
RDEPENDS_${PN}-tracert6 = "${PN}-rltraceroute6"
RDEPENDS_${PN}-misc += "perl"

SRC_URI = "http://www.remlab.net/files/ndisc6/ndisc6-${PV}.tar.bz2 \
           file://merge-hook \
           file://rdnssd.init \
           file://rdnssd.default \
"
SRC_URI[md5sum] = "50cb4c19606cf6ff2b7388e71832f579"
SRC_URI[sha256sum] = "6acec8a0cb9efa3ac98456f46c3016aeec0598b0c7557c95242b5228ad62ca7a"

LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

inherit autotools useradd gettext update-rc.d

ALLOW_EMPTY_${PN} = "0"

# Split into seperate packages since we normal don't want them all
# The main package is left empty and therefore not created.
PACKAGES += "${PN}-ndisc6 ${PN}-tcpspray6 ${PN}-rdisc6 \
    ${PN}-tcptraceroute6 ${PN}-rltraceroute6 \
    ${PN}-tracert6 ${PN}-rdnssd ${PN}-misc"
FILES_${PN}            = ""
FILES_${PN}-ndisc6        = "${bindir}/ndisc6"
FILES_${PN}-tcpspray6         = "${bindir}/tcpspray6"
FILES_${PN}-rdisc6        = "${bindir}/rdisc6"
FILES_${PN}-tcptraceroute6    = "${bindir}/tcptraceroute6"
FILES_${PN}-rltraceroute6    = "${bindir}/rltraceroute6"
FILES_${PN}-tracert6        = "${bindir}/tracert6"
FILES_${PN}-rdnssd        = "${base_sbindir}/rdnssd ${sysconfdir}/rdnssd ${sysconfdir}/init.d ${sysconfdir}/default"
FILES_${PN}-misc                = "${bindir}/dnssort ${bindir}/name2addr ${bindir}/tcpspray ${bindir}/addr2name"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DESCRIPTION_${PN}-ndisc6    = "ICMPv6 Neighbor Discovery tool. \
Performs IPv6 neighbor discovery in userland. Replaces arping from the \
IPv4 world."
DESCRIPTION_${PN}-rdisc6    = "ICMPv6 Router Discovery tool. \
Queries IPv6 routers on the network for advertised prefixes. Can be used \
to detect rogue IPv6 routers, monitor legitimate IPv6 routers."
DESCRIPTION_${PN}-tcpspray6    = "Performs bandwidth measurements of TCP \
sessions between the local system and a remote echo server in either IPv6 \
or IPv4."

DESCRIPTION_${PN}-rdnssd       = "Daemon to autoconfigure the list of DNS \
servers through stateless IPv6 autoconfiguration."

INITSCRIPT_NAME = "rdnssd"
INITSCRIPT_PACKAGES = "${PN}-rdnssd"

USERADD_PACKAGES = "${PN}-rdnssd"
USERADD_PARAM_${PN}-rdnssd = "--system --home /var/run/rdnssd \
                              --no-create-home --shell /bin/false \
                              --gid nogroup rdnssd"

EXTRA_OECONF = "--disable-nls"
PR .= ".1"

do_install_append () {
    rm -rf ${D}${localstatedir}
    # Enable SUID bit for applications that need it
    chmod 4555 ${D}${bindir}/rltraceroute6
    chmod 4555 ${D}${bindir}/ndisc6
    chmod 4555 ${D}${bindir}/rdisc6
    mkdir -p ${D}${base_sbindir}
    mv ${D}${sbindir}/rdnssd ${D}${base_sbindir}/rdnssd
    install -d ${D}/etc/default
    install -m 644 ${WORKDIR}/rdnssd.default ${D}${sysconfdir}/default/rdnssd
    install -d ${D}/etc/init.d
    install -m 755 ${WORKDIR}/rdnssd.init ${D}${sysconfdir}/init.d/rdnssd
    install -m 755 ${WORKDIR}/merge-hook ${D}${sysconfdir}/rdnssd/merge-hook
    rm -r ${D}${sbindir} || true
}

pkg_postrm_ndisc6-rdnssd () {
	deluser rdnssd || true
}
