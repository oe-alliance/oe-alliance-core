HOMEPAGE = "http://hostap.epitest.fi"
SECTION = "kernel/userland"
PRIORITY = "optional"
LICENSE = "BSD"
DEPENDS = "libnl openssl"
LIC_FILES_CHKSUM = "file://README;md5=803e892c4ab1b94914327061ce68446f"

inherit update-rc.d
INITSCRIPT_NAME="hostapd"

do_configure() {
    install -m 0644 ${WORKDIR}/defconfig ${S}/.config
}

do_compile() {
    make
}

do_install() {
    install -d ${D}${sbindir} ${D}${sysconfdir}/init.d
    install -m 0644 ${S}/hostapd.conf ${D}${sysconfdir}
    install -m 0755 ${S}/hostapd ${D}${sbindir}
    install -m 0755 ${S}/hostapd_cli ${D}${sbindir}
    install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/hostapd
}

CONFFILES_${PN} += "${sysconfdir}/hostapd.conf"
