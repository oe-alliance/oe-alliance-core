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

PACKAGES =+ "smbfs smbfs-doc ${PN}-base smbclient ${PN}-base-dbg ${PN}-advanced winbind swat libsmbclient"
PACKAGECONFIG[talloc] = "--enable-external-libtalloc --with-libtalloc, --disable-external-libtalloc --without-libtalloc, talloc"

FILES_${PN}-base       = "${sbindir}/samba_multicall ${sbindir}/smbd ${sbindir}/nmbd \
                          ${bindir}/smbpasswd ${bindir}/smbstatus ${bindir}/smbcontrol ${bindir}/testparm \
                          ${sysconfdir}/init.d/samba \
                          ${sysconfdir}/samba \
                          ${libdir}/samba/*.dat ${base_libdir}/security/pam_smbpass.so \
                          ${sysconfdir}/pam.d/samba"
RRECOMMENDS_${PN}-base+= "wsdd"
FILES_${PN}-base-dbg  += "${base_libdir}/security/.debug/pam_smbpass.so"

FILES_smbclient        = "${bindir}/smbclient"
FILES_libsmbclient     = "${libdir}/libsmbclient.so.*"
FILES_libsmbclient-dev = "${libdir}/libsmbclient.so ${includedir}"

FILES_swat             = "${sbindir}/swat ${datadir}/swat ${libdir}/samba/*.msg"

FILES_winbind         += "${bindir}/ntlm_auth"
FILES_${PN}-advanced   = "${bindir}/net ${bindir}/profiles ${bindir}/rpcclient ${bindir}/smbcacls ${bindir}/smbcquotas ${bindir}/smbget ${bindir}/smbtar ${libdir}/pdb ${libdir}/rpc/*"

INITSCRIPT_PACKAGES = "${PN}-base"
INITSCRIPT_NAME_${PN}-base = "samba"
INITSCRIPT_PARAMS = "defaults"
CONFFILES_${PN}-base = "${sysconfdir}/samba/smb-user.conf ${sysconfdir}/samba/private/users.map ${sysconfdir}/samba/private/smbpasswd"

PACKAGECONFIG ??= ""

do_install_append() {
    rmdir "${D}${localstatedir}/lock"
    rmdir "${D}${localstatedir}/run"
    rmdir --ignore-fail-on-non-empty "${D}${localstatedir}"
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

    rmdir --ignore-fail-on-non-empty ${D}${base_sbindir} || true
    sed -i -e '1s,#!.*perl,#!${USRBINPATH}/env perl,' ${D}${bindir}/findsmb

    # Remove sysinit script if sysvinit is not in DISTRO_FEATURES
    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'false', 'true', d)}; then
        rm -rf ${D}${sysconfdir}/init.d/
    fi

    mkdir -p ${D}${base_libdir}
    mv ${D}${libdir}/security ${D}${base_libdir} || true


    # Silence warnings - Delete empty directories (Removed features)
    rmdir --ignore-fail-on-non-empty ${D}${libdir}/auth || true
    rmdir --ignore-fail-on-non-empty ${D}${libdir}/charset || true
    rmdir --ignore-fail-on-non-empty ${D}${libdir}/vfs || true

    # Former package libwinbind
    rmdir --ignore-fail-on-non-empty ${D}${libdir}/idmap || true
    rmdir --ignore-fail-on-non-empty ${D}${libdir}/gpext || true
    rmdir --ignore-fail-on-non-empty ${D}${libdir}/perfcount || true

    # Former package libnss-winbind
    rmdir --ignore-fail-on-non-empty ${D}${libdir}/nss_info || true
}

pkg_prerm_${PN}-base_prepend() {
#!/bin/sh
grep -v 'pam_smbpass.so' $D/etc/pam.d/common-password > $D/tmp/common-password
mv $D/tmp/common-password $D/etc/pam.d/common-password
rm $D/etc/samba/distro/smb-vmc.conf 2/dev/null || true
}

pkg_postinst_${PN}-base_prepend() {
#!/bin/sh
if [ -n "$D" ]; then
set +e
grep -v 'pam_smbpass.so' $D/etc/pam.d/common-password > $D/tmp/common-password
mv $D/tmp/common-password $D/etc/pam.d/common-password
echo -e "password\toptional\t\t\tpam_smbpass.so nullok use_authtok use_first_pass" >> $D/etc/pam.d/common-password
fi

if [ -n "$D" ]; then
	grep -qE '^kids:' $D/etc/passwd
	if [[ $? -ne 0 ]] ; then
		echo 'kids:x:500:500:Linux User,,,:/media:/bin/false' >> $D/etc/passwd
		echo 'kids:!:16560:0:99999:7:::' >> $D/etc/shadow
	fi
fi

if [ -z "$D" ]; then
	[ -e /etc/samba/private/smbpasswd ] || touch /etc/samba/private/smbpasswd

	grep -qE '^root:' /etc/samba/private/smbpasswd
	if [[ $? -ne 0 ]] ; then
		smbpasswd -Ln root >/dev/null
	fi

	grep -qE '^kids:' /etc/passwd
	if [[ $? -ne 0 ]] ; then
		adduser -h /media -s /bin/false -H -D -u 500 kids 2>/dev/null || adduser -h /media -s /bin/false -H -D kids
	fi

	grep -qE '^kids:' /etc/samba/private/smbpasswd
	if [[ $? -ne 0 ]] ; then
		smbpasswd -Ln kids >/dev/null
	fi
fi

if [ -e $D/etc/samba/distro/smb-vmc.vmc ]; then
        rm $D/etc/samba/distro/smb-vmc.conf 2/dev/null || true
        ln -s smb-vmc.vmc $D/etc/samba/distro/smb-vmc.conf
else
        rm $D/etc/samba/distro/smb-vmc.conf 2/dev/null || true
        ln -s smb-vmc.samba $D/etc/samba/distro/smb-vmc.conf
fi
}
