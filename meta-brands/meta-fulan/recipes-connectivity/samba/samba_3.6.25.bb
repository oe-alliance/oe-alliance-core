require samba36.inc

inherit update-rc.d upx-compress

SRC_URI += "file://smb.conf \
           file://smb-user.conf \
           file://smb-branding.conf \
           file://smb-global.conf \
           file://smb-insecure.conf \
           file://smb-secure.conf \
           file://smb-shares.conf \
           file://smb-vmc.samba \
           file://init.samba \
           file://pam.samba \
           file://users.map \
           file://smbpasswd \
"

do_install_append() {
    rm -rf ${D}${localstatedir}/lock
    rm -rf ${D}${localstatedir}/run
    rm -rf ${D}${localstatedir}
    rm -f ${D}${bindir}/*.old
    rm -f ${D}${sbindir}/*.old

    ln -sf ./samba_multicall ${D}${sbindir}/smbd
    ln -sf ./samba_multicall ${D}${sbindir}/nmbd
    ln -sf ../sbin/samba_multicall ${D}${bindir}/smbpasswd

    install -d ${D}${sysconfdir}/samba
    install -d ${D}${sysconfdir}/samba/distro
    install -d ${D}${sysconfdir}/samba/private

    install -D -m 755 ${WORKDIR}/init.samba ${D}${sysconfdir}/init.d/samba
    install -m 644 ${WORKDIR}/smb.conf ${D}${sysconfdir}/samba
    install -m 644 ${WORKDIR}/smb-user.conf ${D}${sysconfdir}/samba
    install -m 644 ${WORKDIR}/smb-branding.conf ${D}${sysconfdir}/samba/distro
    install -m 644 ${WORKDIR}/smb-global.conf ${D}${sysconfdir}/samba/distro
    install -m 644 ${WORKDIR}/smb-insecure.conf ${D}${sysconfdir}/samba/distro
    install -m 644 ${WORKDIR}/smb-secure.conf ${D}${sysconfdir}/samba/distro
    install -m 644 ${WORKDIR}/smb-shares.conf ${D}${sysconfdir}/samba/distro
    install -m 644 ${WORKDIR}/smb-vmc.samba ${D}${sysconfdir}/samba/distro
    install -m 600 ${WORKDIR}/users.map ${D}${sysconfdir}/samba/private/users.map
    install -m 600 ${WORKDIR}/smbpasswd ${D}${sysconfdir}/samba/private/smbpasswd
    install -D -m 644 ${WORKDIR}/pam.samba ${D}${sysconfdir}/pam.d/samba

    rm -rf ${D}${base_sbindir}
    sed -i -e '1s,#!.*perl,#!${USRBINPATH}/env perl,' ${D}${bindir}/findsmb

    # Remove sysinit script if sysvinit is not in DISTRO_FEATURES
    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'false', 'true', d)}; then
        rm -rf ${D}${sysconfdir}/init.d/
    fi

    mkdir -p ${D}${base_libdir}
    mv ${D}${libdir}/security ${D}${base_libdir} || true

    # Silence warnings - Delete empty directories (Removed features)
    rm -rf ${D}${libdir}/auth
    rm -rf ${D}${libdir}/charset
    rm -rf ${D}${libdir}/vfs

    # Former package libwinbind
    rm -rf ${D}${libdir}/idmap
    rm -rf ${D}${libdir}/gpext
    rm -rf ${D}${libdir}/perfcount

    # Former package libnss-winbind
    rm -rf ${D}${libdir}/nss_info
}

require samba36-pkgs.inc

INSANE_SKIP += "dev-so"
