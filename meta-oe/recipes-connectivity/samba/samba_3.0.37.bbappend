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

PACKAGES =+ "smbfs smbfs-doc sambaserver libpopt libtalloc smbclient sambaserver-vfs samba-advanced winbind libwinbind libnss-winbind"

FILES_winbind = "${bindir}/ntlm_auth"
FILES_libwinbind = "${libdir}/idmap/*.so \
                    ${libdir}/gpext \
                    ${libdir}/perfcount \
                    ${libdir}/security/pam_winbind.so"
FILES_libnss-winbind = "${libdir}/libnss_*${SOLIBS} \
                        ${libdir}/nss_info"
FILES_samba-advanced = "${bindir}/net ${bindir}/profiles ${bindir}/rpcclient ${bindir}/smbcacls ${bindir}/smbcquotas ${bindir}/smbget ${bindir}/smbtar ${libdir}/rpc/*"
FILES_sambaserver-vfs = "${libdir}/vfs/*"
FILES_smbclient = "${bindir}/smbclient"
FILES_smbfs = "${bindir}/smbmount ${bindir}/smbumount ${bindir}/smbmnt ${base_sbindir}/mount.smbfs ${base_sbindir}/mount.smb"
FILES_smbfs-doc = "${mandir}/man8/smbmount.8 ${mandir}/man8/smbumount.8 ${mandir}/man8/smbmnt.8"
FILES_sambaserver = "${sbindir}/smbd ${sbindir}/nmbd ${libdir}/charset/*.so ${libdir}/*.dat \
    ${sysconfdir}/samba/smb.conf ${sysconfdir}/samba/private ${sysconfdir}/init.d/samba"
FILES_libpopt = "${libdir}/libpopt.so.*"
FILES_libtalloc = "${libdir}/libtalloc.so.*"

INITSCRIPT_PACKAGES = "${PN}server"
INITSCRIPT_NAME_${PN}server = "samba"
INITSCRIPT_PARAMS = "defaults"
CONFFILES_${PN}server = "${sysconfdir}/samba/smb.conf"
RDEPENDS_sambaserver += "busybox-inetd"

do_install_prepend() {
    install -c -m 644 ${WORKDIR}/smb.conf ../examples/smb.conf.default
}

do_install_append() {
    install -d ${D}${sysconfdir}/samba/private
}

pkg_preinst_sambaserver_prepend() {
#!/bin/sh
if [ -e $D/etc/init.d/samba ]; then
	chattr -i $D/etc/init.d/samba*
fi

# Remove if-*.d start/stop scripts
LOC=$D/etc/network
for SCRIPT in $LOC/if-up.d/01samba-start $LOC/if-down.d/01samba-kill $LOC/if-post-down.d/01samba-kill
do
	if [ -e $SCRIPT ]; then
		chattr -i $SCRIPT
		rm $SCRIPT
	fi
done
}

pkg_postinst_sambaserver_prepend() {
#!/bin/sh
# For cosmetical reasons we want Samba to be added before streaming
if grep -qE '^[#\s]*\(8001|8002|8003\)' $D/etc/inetd.conf;
then
	# Streaming port(s) in inetd - Insert Samba before
	for PORT in 8001 8002 8003
	do
		if grep -qE "^[#\s]*$PORT" $D/etc/inetd.conf;
		then
			if ! grep -qE '^#?(445|microsoft-ds)' $D/etc/inetd.conf; then
				sed -i "s#^\(\#*$PORT\)#microsoft-ds\tstream\ttcp6\tnowait\troot\t/usr/sbin/smbd\t\t\tsmbd\n\1#" $D/etc/inetd.conf
			fi
			if ! grep -qE '^#?(137|netbios-ns)' $D/etc/inetd.conf; then
				sed -i "s#^\(\#*$PORT\)#netbios-ns\tdgram\tudp\twait\troot\t/usr/sbin/nmbd\t\t\tnmbd\n\1#" $D/etc/inetd.conf
			fi
			break
		fi
	done

else

	# No streaming ports in inetd - Append Samba
	if ! grep -qE '^#?(445|microsoft-ds)' $D/etc/inetd.conf; then
	        echo -e "microsoft-ds\tstream\ttcp6\tnowait\troot\t/usr/sbin/smbd\t\t\tsmbd" >> $D/etc/inetd.conf
	fi
	if ! grep -qE '^#?(137|netbios-ns)' $D/etc/inetd.conf; then
		echo -e "netbios-ns\tdgram\tudp\twait\troot\t/usr/sbin/nmbd\t\t\tnmbd" >> $D/etc/inetd.conf
	fi
fi

# Unify port numbers/names to names
sed -i "s/^\(\#*\)445/\1microsoft-ds/g" $D/etc/inetd.conf
sed -i "s/^\(\#*\)137/\1netbios-ns/g"   $D/etc/inetd.conf
sed -i "s/^\(\#*\)138/\1netbios-dgm/g"  $D/etc/inetd.conf

grep -vE '^[#\s]*netbios-dgm' $D/etc/inetd.conf > $D/tmp/inetd.tmp
mv $D/tmp/inetd.tmp $D/etc/inetd.conf

if [ -z "$D" -a -f "/etc/init.d/inetd.busybox" ]; then
	# Restart the internet superserver
	/etc/init.d/inetd.busybox restart
fi

# Dirty work-around for an update continuity problem
if [ -z "$D" ]; then
	chattr +i $D/etc/init.d/samba
fi

}

pkg_prerm_sambaserver_prepend() {
#!/bin/sh
chattr -i $D/etc/init.d/samba

grep -vE '^[#\s]*(445|microsoft-ds|137|netbios-ns|138|netbios-dgm|139|netbios-ssn)' $D/etc/inetd.conf > $D/tmp/inetd.tmp
mv $D/tmp/inetd.tmp $D/etc/inetd.conf

if [ -z "$D" -a -f "/etc/init.d/inetd.busybox" ]; then
	/etc/init.d/inetd.busybox restart
	killall -9 smbd nmbd >/dev/null 2>&1
fi
}
