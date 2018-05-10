inherit upx-compress

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://vsftpd.chroot_list \
            file://ftp.service \
            file://login-blank-password.patch \
            file://fixchroot.patch \
           "

CONFFILES_${PN} += "${sysconfdir}/vsftpd.chroot_list"

do_install_append() {
    rm ${D}${sysconfdir}/vsftpd.user_list
    install -m 600 ${WORKDIR}/vsftpd.chroot_list ${D}${sysconfdir}/vsftpd.chroot_list
    mkdir -p ${D}${sysconfdir}/avahi/services
    install -m 644 ${WORKDIR}/ftp.service ${D}${sysconfdir}/avahi/services
    if ! test -z ${PAMLIB} ; then
	grep -v 'pam_shells.so' ${D}${sysconfdir}/pam.d/vsftpd > $D/tmp/vsftpd
	mv $D/tmp/vsftpd ${D}${sysconfdir}/pam.d/vsftpd
    fi
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        rm ${D}/etc/init.d/vsftpd || true
    fi
}

pkg_postinst_${PN}_prepend() {
#!/bin/sh

if [ -n "$D" ]; then
	grep -qE '^kids:' $D/etc/passwd
	if [[ $? -ne 0 ]] ; then
		echo 'kids:x:500:500:Linux User,,,:/media:/bin/false' >> $D/etc/passwd
		echo 'kids:!:16560:0:99999:7:::' >> $D/etc/shadow
	fi
fi

if [ -z "$D" ]; then
	grep -qE '^kids:' /etc/passwd
	if [[ $? -ne 0 ]] ; then
		adduser -h /media -s /bin/false -H -D -u 500 kids 2>/dev/null || adduser -h /media -s /bin/false -H -D kids
	fi

fi
}
