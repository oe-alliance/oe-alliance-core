SUMMARY = "Kernel modules for tunneling"
DESCRIPTION:${PN}  = "Kernel module for generic IP tunnel support"
DESCRIPTION:${PN}4 = "Kernel modules for IPv4 tunneling"
DESCRIPTION:${PN}6 = "Kernel modules for IPv6 tunneling"

require conf/license/license-gplv2.inc

SRC_URI = "file://31-iptunnel \
           file://31-iptunnel4 \
           file://31-iptunnel6 \
          "

do_install() {
        install -d ${D}${sysconfdir}/modules.d
        install -m 0755 ${S}/31-iptunnel ${D}${sysconfdir}/modules.d
        install -m 0755 ${S}/31-iptunnel4 ${D}${sysconfdir}/modules.d
        install -m 0755 ${S}/31-iptunnel6 ${D}${sysconfdir}/modules.d
}

PACKAGES =+ "${PN}4 ${PN}6"
RDEPENDS:${PN}4 += "${PN}"
RDEPENDS:${PN}6 += "${PN}"

FILES:${PN}  = "${sysconfdir}/modules.d/31-iptunnel"
FILES:${PN}4 = "${sysconfdir}/modules.d/31-iptunnel4"
FILES:${PN}6 = "${sysconfdir}/modules.d/31-iptunnel6"

RRECOMMENDS:${PN}  += "kernel-module-ip-tunnel"
RRECOMMENDS:${PN}4 += "kernel-module-tunnel4"
RRECOMMENDS:${PN}6 += "kernel-module-tunnel6"

# We are building a pure meta-package
INSANE_SKIP:${PN}  += "build-deps"
INSANE_SKIP:${PN}4 += "build-deps"
INSANE_SKIP:${PN}6 += "build-deps"
