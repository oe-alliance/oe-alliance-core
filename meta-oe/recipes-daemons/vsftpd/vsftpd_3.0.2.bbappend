SRC_URI += "file://vsftpd.chroot_list \
            file://init.vsftpd \
            file://ftp.service \
           "

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "${PN}"
INITSCRIPT_PARAMS = "defaults"

CONFFILES_${PN} += "${sysconfdir}/vsftpd.chroot_list"

do_install_append() {
    install -m 600 ${WORKDIR}/vsftpd.chroot_list ${D}${sysconfdir}/vsftpd.chroot_list
    mkdir -p ${D}${sysconfdir}/init.d
    install -m 755 ${WORKDIR}/init.vsftpd ${D}${sysconfdir}/init.d/vsftpd
    mkdir -p ${D}${sysconfdir}/avahi/services
    install -m 644 ${WORKDIR}/ftp.service ${D}${sysconfdir}/avahi/services
    if ! test -z ${PAMLIB} ; then
	grep -v 'pam_shells.so' ${D}${sysconfdir}/pam.d/vsftpd > $D/tmp/vsftpd
	mv $D/tmp/vsftpd ${D}${sysconfdir}/pam.d/vsftpd
    fi
}

pkg_preinst_${PN}_prepend() {
#!/bin/sh

# Remove ftp inetd.conf entries
if [ -z "$D" -a -f "/etc/inetd.conf" ]; then
	grep -vE '^[#\s]*(21|ftp)' $D/etc/inetd.conf > $D/tmp/inetd.tmp
	mv $D/tmp/inetd.tmp $D/etc/inetd.conf
fi

if [ -z "$D" -a -f "/etc/init.d/inetd.busybox" ]; then
	# Restart the internet superserver
	/etc/init.d/inetd.busybox restart
fi

if [ -z "$D" -a -f "/etc/vsftpd.conf" ]; then
	echo "Existing user modified configs might make vsftpd fail to start!"
	echo "Renaming config file /etc/vsftpd.conf to /etc/vsftpd.conf-user ..."
	mv /etc/vsftpd.conf /etc/vsftpd.conf-user
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
