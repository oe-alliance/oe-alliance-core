FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PACKAGECONFIG = ""

SRC_URI_remove_sh4 += "file://Using-PKG_CHECK_MODULES-to-found-headers-and-libraries.patch"

SRC_URI += "file://smbnetfs.common.conf file://smbnetfs.user.conf file://init file://smbclient.pc"

do_configure_prepend() {
    if [ ! -e ${STAGING_LIBDIR}/pkgconfig/smbclient.pc ]; then
    cp ${WORKDIR}/smbclient.pc ${STAGING_LIBDIR}/pkgconfig/
    fi
}

FILES_${PN} += "${sysconfdir}/*.conf ${sysconfdir}/init.d"
CONFFILES_${PN} = "${sysconfdir}/smbnetfs.user.conf"
inherit update-rc.d

INITSCRIPT_NAME = "${PN}.sh"

do_install_append() {
    install -d ${D}${sysconfdir}
    install -m 600 ${WORKDIR}/smbnetfs.common.conf ${D}${sysconfdir}/
    install -m 600 ${WORKDIR}/smbnetfs.user.conf ${D}${sysconfdir}/
    install -d ${D}${sysconfdir}/init.d
    install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/${PN}.sh
}
