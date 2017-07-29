FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"
#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Remove acl, cups etc. support.
PACKAGECONFIG_remove = "acl cups"

PR="r0"

EXTRA_OECONF += " \
                 --without-cluster-support \
                 --without-profiling-data \
                 --with-sockets-dir=${localstatedir}/run \
                 --with-logfilebase=${localstatedir}/log \
                 --nopyc \
                "

EXTRA_OECONF_remove = " \
                       --with-cluster-support \
                       --with-profiling-data \
                       --with-sockets-dir=${localstatedir}/run/samba \
                      "

# Remove unused, add own config, init script
SRC_URI += " \
           file://smb.conf \
           file://init.samba \
           file://pam.samba \
           file://users.map \
           file://smbpasswd \
           "

do_install_append() {
#	rm -fR ${D}/var
	rm -fR ${D}/run
	rm -fR ${D}${libdir}/tmpfiles.d
	rm -fR ${D}${sysconfdir}/sysconfig
	install -d ${D}${sysconfdir}/pam.d
        install -m 644 ${WORKDIR}/pam.samba ${D}${sysconfdir}/pam.d/samba
	install -d ${D}${sysconfdir}/samba
	install -d ${D}${sysconfdir}/samba/private
	install -m 644 ${WORKDIR}/smb.conf ${D}${sysconfdir}/samba
	install -m 644 ${WORKDIR}/smbpasswd ${D}${sysconfdir}/samba/private
	install -m 644 ${WORKDIR}/users.map ${D}${sysconfdir}/samba/private
	install -d ${D}${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/init.samba ${D}${sysconfdir}/init.d/samba
}

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}-base"
INITSCRIPT_NAME_${PN}-base = "samba"
INITSCRIPT_PARAMS_${PN}-base = "defaults"

# workaround to get rid of perl dependency
DEPENDS_remove = "perl"
RDEPENDS_${PN}_remove = "perl"

PACKAGES =+ "libmessages-util-samba4 libwinbind-client-samba4 libtalloc-report-samba4 ${PN}-advanced"
RDEPENDS_${PN}-base_append = "${BPN}-common libmessages-util-samba4 libwinbind-client-samba4 libtalloc-report-samba4"
# libmessages-util-samba4
FILES_${PN}-base      += "${bindir}/smbpasswd ${bindir}/testparm \
                          ${bindir}/smbcontrol ${bindir}/smbstatus \
                          ${sysconfdir}/init.d/samba"
FILES_${BPN}-common   += "${sysconfdir}/pam.d/samba"

FILES_smbclient = "${bindir}/smbclient"

FILES_${PN}-advanced = "${bindir}/cifsdd \
                   ${bindir}/rpcclient \
                   ${bindir}/smbcacls \
                   ${bindir}/smbcquotas \
                   ${bindir}/smbget \
                   ${bindir}/smbspool \
                   ${bindir}/smbtar \
                   ${bindir}/smbtree \
                   ${libdir}/samba/smbspool_krb5_wrapper"

CONFFILES_${BPN}-common = "${sysconfdir}/pam.d/samba ${sysconfdir}/samba/smb.conf ${sysconfdir}/samba/private/users.map ${sysconfdir}/samba/private/smbpasswd"

RRECOMMENDS_${PN}-base+= "wsdd"


pkg_prerm_${BPN}-common_prepend() {
#!/bin/sh
grep -v 'pam_smbpass.so' $D/etc/pam.d/common-password > $D/tmp/common-password
mv $D/tmp/common-password $D/etc/pam.d/common-password
}

pkg_postinst_${BPN}-common_prepend() {
#!/bin/sh

grep -v 'pam_smbpass.so' $D/etc/pam.d/common-password > $D/tmp/common-password
mv $D/tmp/common-password $D/etc/pam.d/common-password
echo -e "password\toptional\t\t\tpam_smbpass.so nullok use_authtok use_first_pass" >> $D/etc/pam.d/common-password

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
}

inherit binary-compress

FILES_COMPRESS_dm800se = "${bindir}/smbclient ${bindir}/smbpasswd ${bindir}/testparm \
                          ${bindir}/smbcontrol ${bindir}/smbstatus \
                          ${sbindir}/nmbd ${sbindir}/smbd \
                         "
FILES_COMPRESS_dm500hd = "${bindir}/smbclient ${bindir}/smbpasswd ${bindir}/testparm \
                          ${bindir}/smbcontrol ${bindir}/smbstatus \
                          ${sbindir}/nmbd ${sbindir}/smbd \
                         "
FILES_COMPRESS_dm800 = "${bindir}/smbclient ${bindir}/smbpasswd ${bindir}/testparm \
                          ${bindir}/smbcontrol ${bindir}/smbstatus \
                          ${sbindir}/nmbd ${sbindir}/smbd \
                         "
