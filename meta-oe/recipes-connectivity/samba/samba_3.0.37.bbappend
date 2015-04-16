EXTRA_OECONF += " \
    samba_cv_LINUX_LFS_SUPPORT=yes \
    samba_cv_HAVE_OFF64_T=yes \
    samba_cv_have_longlong=yes \
    samba_cv_HAVE_UNSIGNED_CHAR=yes \
    samba_cv_HAVE_GETTIMEOFDAY_TZ=yes \
    samba_cv_HAVE_C99_VSNPRINTF=yes \
    samba_cv_HAVE_BROKEN_READDIR=no \
    samba_cv_HAVE_IFACE_IFCONF=yes \
    "

PACKAGES =+ "smbfs smbfs-doc sambaserver libpopt libtalloc smbclient"

FILES_smbclient = "${bindir}/smbclient"
FILES_smbfs = "${bindir}/smbmount ${bindir}/smbumount ${bindir}/smbmnt ${base_sbindir}/mount.smbfs ${base_sbindir}/mount.smb"
FILES_smbfs-doc = "${mandir}/man8/smbmount.8 ${mandir}/man8/smbumount.8 ${mandir}/man8/smbmnt.8"
FILES_sambaserver = "${sbindir}/smbd ${sbindir}/nmbd ${libdir}/charset/*.so ${libdir}/*.dat \
    ${sysconfdir}/samba/smb.conf ${sysconfdir}/samba/private ${sysconfdir}/init.d/samba"
FILES_libpopt = "${libdir}/libpopt.so.*"
FILES_libtalloc = "${libdir}/libtalloc.so.*"

CONFFILES_${PN} = ""
CONFFILES_sambaserver = "${sysconfdir}/samba/smb.conf"

do_install_prepend() {
    install -c -m 644 ${WORKDIR}/smb.conf ../examples/smb.conf.default
}

do_install_append() {
    install -d ${D}${sysconfdir}/samba/private
}

pkg_preinst_sambaserver() {
#!/bin/sh
if [ -z "$D" -a -f "/etc/init.d/samba" ]; then
	/etc/init.d/samba stop
fi
if type update-rc.d >/dev/null 2>/dev/null; then
	if [ -n "$D" ]; then
		OPT="-f -r $D"
	else
		OPT="-f"
	fi
	update-rc.d $OPT samba remove
fi

# Remove SysVinit start for Samba
if [ -e /etc/init.d/samba ]; then
	chattr -i /etc/init.d/samba*
	rm /etc/init.d/samba*
fi

# Remove if-*.d start/stop scripts
LOC=/etc/network
for SCRIPT in $LOC/if-up.d/01samba-start $LOC/if-down.d/01samba-kill $LOC/if-post-down.d/01samba-kill
do
	if [ -e $SCRIPT ]; then
		chattr -i $SCRIPT
		rm $SCRIPT
	fi
done
}

pkg_postinst_sambaserver() {
#!/bin/sh

# For cosmetical reasons we want Samba to be added before streaming
if grep -qE '^#?\(8001|8002|8003\)' /etc/inetd.conf;
then
	# Streaming port(s) in inetd - Insert Samba before
	for PORT in 8001 8002 8003
	do
		if grep -qE "^#?$PORT" /etc/inetd.conf;
		then
			if ! grep -qE '^#?(445|microsoft-ds)' /etc/inetd.conf; then
				sed -i "s#^\(\#*$PORT\)#microsoft-ds\tstream\ttcp6\tnowait\troot\t/usr/sbin/smbd\t\t\tsmbd\n\1#" /etc/inetd.conf
			fi
			if ! grep -qE '^#?(137|netbios-ns)' /etc/inetd.conf; then
				sed -i "s#^\(\#*$PORT\)#netbios-ns\tdgram\tudp\twait\troot\t/usr/sbin/nmbd\t\t\tnmbd\n\1#" /etc/inetd.conf
			fi
			break
		fi
	done

else

	# No streaming ports in inetd - Append Samba
	if ! grep -qE '^#?(445|microsoft-ds)' /etc/inetd.conf; then
	        echo -e "microsoft-ds\tstream\ttcp6\tnowait\troot\t/usr/sbin/smbd\t\t\tsmbd" >> /etc/inetd.conf
	fi
	if ! grep -qE '^#?(137|netbios-ns)' /etc/inetd.conf; then
		echo -e "netbios-ns\tdgram\tudp\twait\troot\t/usr/sbin/nmbd\t\t\tnmbd" >> /etc/inetd.conf
	fi
fi

# Unify port numbers/names to names
sed -i "s/^\(\#*\)445/\1microsoft-ds/g" /etc/inetd.conf
sed -i "s/^\(\#*\)137/\1netbios-ns/g"   /etc/inetd.conf
sed -i "s/^\(\#*\)138/\1netbios-dgm/g"  /etc/inetd.conf

grep -vE '^#?netbios-dgm' /etc/inetd.conf > /tmp/inetd.tmp
mv /tmp/inetd.tmp /etc/inetd.conf

# Restart the internet superserver
/etc/init.d/inetd.busybox restart

}

pkg_prerm_sambaserver() {
#!/bin/sh
if type update-rc.d >/dev/null 2>/dev/null; then
	if [ -n "$D" ]; then
		OPT="-r $D"
	else
		OPT=""
	fi
	update-rc.d $OPT samba remove >/dev/null 2>&1
fi

grep -vE '^#?(445|microsoft-ds|137|netbios-ns|138|netbios-dgm|139|netbios-ssn)' /etc/inetd.conf > /tmp/inetd.tmp
mv /tmp/inetd.tmp /etc/inetd.conf

/etc/init.d/inetd.busybox restart

killall -9 smbd nmbd >/dev/null 2>&1
}
