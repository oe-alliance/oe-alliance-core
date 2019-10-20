inherit upx-compress

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://ftp.service"

LDFLAGS_append =" -lssl -lcrypto"

do_configure_prepend() {
    sed -i 's#undef VSF_BUILD_SSL#define VSF_BUILD_SSL#' ${S}/builddefs.h
}

do_install_append() {
    rm ${D}${sysconfdir}/vsftpd.user_list
    mkdir -p ${D}${sysconfdir}/avahi/services
    install -m 644 ${WORKDIR}/ftp.service ${D}${sysconfdir}/avahi/services
    if ! test -z ${PAMLIB} ; then
    grep -v 'pam_shells.so' ${D}${sysconfdir}/pam.d/vsftpd > $D/tmp/vsftpd
    mv $D/tmp/vsftpd ${D}${sysconfdir}/pam.d/vsftpd
    fi
    sed -i "s: nullok::" ${D}${sysconfdir}/pam.d/vsftpd
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        rm ${D}/etc/init.d/vsftpd || true
    fi
    chown root ${D}${sysconfdir}/vsftpd.conf
}


pkg_postinst_${PN}_append() {
#!/bin/sh

if [ -n "$D" ]; then
	set +e
	grep -qE '^kids:' $D/etc/passwd
	if [[ $? -ne 0 ]] ; then
		echo 'kids:x:500:500:Linux User,,,:/media:/bin/false' >> $D/etc/passwd
		echo 'kids:!:16560:0:99999:7:::' >> $D/etc/shadow
	fi
fi


if [ -z "$D" ]; then
	set +e
	grep -qE '^kids:' /etc/passwd
	if [[ $? -ne 0 ]] ; then
		adduser -h /media -s /bin/false -H -D -u 500 kids 2>/dev/null || adduser -h /media -s /bin/false -H -D kids
	fi

fi
}
