SUMMARY = "Kernel modules for tunneling"
DESCRIPTION_${PN}  = "Kernel module for generic IP tunnel support"
DESCRIPTION_${PN}4 = "Kernel modules for IPv4 tunneling"
DESCRIPTION_${PN}6 = "Kernel modules for IPv6 tunneling"

require conf/license/license-gplv2.inc

SRC_URI = "file://31-iptunnel \
           file://31-iptunnel4 \
           file://31-iptunnel6 \
          "

do_install() {
        install -d ${D}${sysconfdir}/modules.d
        install -m 0755 ${WORKDIR}/31-iptunnel ${D}${sysconfdir}/modules.d
        install -m 0755 ${WORKDIR}/31-iptunnel4 ${D}${sysconfdir}/modules.d
        install -m 0755 ${WORKDIR}/31-iptunnel6 ${D}${sysconfdir}/modules.d
}

PACKAGES =+ "${PN}4 ${PN}6"
RDEPENDS_${PN}4 += "${PN}"
RDEPENDS_${PN}6 += "${PN}"

FILES_${PN}  = "${sysconfdir}/modules.d/31-iptunnel"
FILES_${PN}4 = "${sysconfdir}/modules.d/31-iptunnel4"
FILES_${PN}6 = "${sysconfdir}/modules.d/31-iptunnel6"

RRECOMMENDS_${PN}  += "kernel-module-ip-tunnel"
RRECOMMENDS_${PN}4 += "kernel-module-tunnel4"
RRECOMMENDS_${PN}6 += "kernel-module-tunnel6"

# We are building a pure meta-package
INSANE_SKIP_${PN}  += "build-deps"
INSANE_SKIP_${PN}4 += "build-deps"
INSANE_SKIP_${PN}6 += "build-deps"
