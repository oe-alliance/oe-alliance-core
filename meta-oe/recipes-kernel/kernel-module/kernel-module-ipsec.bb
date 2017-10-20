SUMMARY = "Kernel modules for IPsec support"
DESCRIPTION_${PN}  = "Kernel modules for IPsec support in both IPv4 and IPv6."
DESCRIPTION_${PN}4 = "Kernel modules for IPsec support in IPv4."
DESCRIPTION_${PN}6 = "Kernel modules for IPsec support in IPv6."

require conf/license/license-gplv2.inc

SRC_URI = "file://30-ipsec \
           file://32-ipsec4 \
           file://32-ipsec6 \
          "

do_install() {
        install -d ${D}${sysconfdir}/modules.d
        install -m 0644 ${WORKDIR}/30-ipsec ${D}${sysconfdir}/modules.d
        install -m 0644 ${WORKDIR}/32-ipsec4 ${D}${sysconfdir}/modules.d
        install -m 0644 ${WORKDIR}/32-ipsec6 ${D}${sysconfdir}/modules.d
}

PACKAGES =+ "${PN}4 ${PN}6"
RDEPENDS_${PN}4 += "${PN}"
RDEPENDS_${PN}6 += "${PN}"

FILES_${PN}  = "${sysconfdir}/modules.d/30-ipsec"
FILES_${PN}4 = "${sysconfdir}/modules.d/32-ipsec4"
FILES_${PN}6 = "${sysconfdir}/modules.d/32-ipsec6"

RRECOMMENDS_${PN}  += "kernel-module-iptunnel \
                       \
                       kernel-module-af-key \
                       kernel-module-xfrm-algo \
                       kernel-module-xfrm-ipcomp \
                       kernel-module-xfrm-user \
                       \
                       kernel-module-authenc \
                       kernel-module-cbc \
                       kernel-module-deflate \
                       kernel-module-des \
                       kernel-module-echainiv \
                       kernel-module-hmac \
                       kernel-module-iv \
                       kernel-module-md5 \
                       kernel-module-sha1 \
                       \
                       kernel-module-ipsec4 \
                       kernel-module-ipsec6"

RRECOMMENDS_${PN}4 += "kernel-module-iptunnel4 \
                       kernel-module-ah4 \
                       kernel-module-esp4 \
                       kernel-module-ipcomp \
                       kernel-module-xfrm4-mode-beet \
                       kernel-module-xfrm4-mode-transport \
                       kernel-module-xfrm4-mode-tunnel \
                       kernel-module-xfrm4-tunnel"

RRECOMMENDS_${PN}6 += "kernel-module-iptunnel6 \
                       kernel-module-ah6 \
                       kernel-module-esp6 \
                       kernel-module-ipcomp6 \
                       kernel-module-xfrm6-mode-beet \
                       kernel-module-xfrm6-mode-transport \
                       kernel-module-xfrm6-mode-tunnel \
                       kernel-module-xfrm6-tunnel"

# We are building a pure meta-package
INSANE_SKIP_${PN}  += "build-deps"
INSANE_SKIP_${PN}4 += "build-deps"
INSANE_SKIP_${PN}6 += "build-deps"
