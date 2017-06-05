LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://../COPYING;md5=d32239bcb673463ab874e80d47fae504"

SECTION = "console/network"
DEPENDS = "readline virtual/libiconv zlib popt libpam libtalloc attr avahi"

PR = "r8"

inherit autotools-brokensep update-rc.d

SAMBA_MIRROR = "http://samba.org/samba/ftp"

MIRRORS += "\ 
${SAMBA_MIRROR}    http://mirror.internode.on.net/pub/samba \n \
${SAMBA_MIRROR}    http://www.mirrorservice.org/sites/ftp.samba.org \n \
"

SRC_URI = "${SAMBA_MIRROR}/stable/samba-${PV}.tar.gz \
           file://011-patch-cve-2015-5296.patch;patchdir=.. \
           file://012-patch-cve-2015-5299.patch;patchdir=.. \
           file://015-patch-cve-2015-7560.patch;patchdir=.. \
           file://020-CVE-preparation-v3-6.patch;patchdir=.. \
           file://021-CVE-preparation-v3-6-addition.patch;patchdir=.. \
           file://022-CVE-2015-5370-v3-6.patch;patchdir=.. \
           file://023-CVE-2016-2110-v3-6.patch;patchdir=.. \
           file://024-CVE-2016-2111-v3-6.patch;patchdir=.. \
           file://025-CVE-2016-2112-v3-6.patch;patchdir=.. \
           file://026-CVE-2016-2115-v3-6.patch;patchdir=.. \
           file://027-CVE-2016-2118-v3-6.patch;patchdir=.. \
           file://028-CVE-2017-7494-v3-6.patch;patchdir=.. \
           file://100-configure_fixes.patch;patchdir=.. \
           file://110-multicall.patch;patchdir=.. \
           file://111-owrt_smbpasswd.patch;patchdir=.. \
           file://120-add_missing_ifdef.patch;patchdir=.. \
           file://200-remove_printer_support.patch;patchdir=.. \
           file://210-remove_ad_support.patch;patchdir=.. \
           file://220-remove_services.patch;patchdir=.. \
           file://230-remove_winreg_support.patch;patchdir=.. \
           file://240-remove_dfs_api.patch;patchdir=.. \
           file://250-remove_domain_logon.patch;patchdir=.. \
           file://260-remove_samr.patch;patchdir=.. \
           file://270-remove_registry_backend.patch;patchdir=.. \
           file://280-strip_srvsvc.patch;patchdir=.. \
           file://290-remove_lsa.patch;patchdir=.. \
           file://300-assert_debug_level.patch;patchdir=.. \
           file://310-remove_error_strings.patch;patchdir=.. \
           file://320-debug_level_checks.patch;patchdir=.. \
           file://330-librpc_default_print.patch;patchdir=.. \
           file://smb.conf \
           file://init.samba \
           file://pam.samba \
           file://users.map \
           file://smbpasswd \
"

# Intentionally left out this patch:
#           file://010-patch-cve-2015-5252.patch;patchdir=.. 
#
# Including it would force us to allow "wide links", which opens a bigger security hole than the one which
# we would close.




SRC_URI[md5sum] = "76da2fa64edd94a0188531e7ecb27c4e"
SRC_URI[sha256sum] = "8f2c8a7f2bd89b0dfd228ed917815852f7c625b2bc0936304ac3ed63aaf83751"

SAMBAMMAP = "no"
SAMBAMMAP_libc-glibc = "yes"

# The file system settings --foodir=dirfoo and overridden unconditionally
# in the samba config by --with-foodir=dirfoo - even if the --with is not
# specified!  Fix that here.  Set the privatedir to /etc/samba/private.
EXTRA_OECONF="--exec-prefix=/usr \
              --with-readline=${STAGING_LIBDIR}/.. \
              --with-libiconv=${STAGING_LIBDIR}/.. \
              --enable-avahi \
              --disable-cups \
              --disable-pie \
              --disable-relro \
              --disable-static \
              --disable-shared-libs \
              --enable-shared \
              --with-configdir=${sysconfdir}/samba \
              --with-privatedir=${sysconfdir}/samba/private \
              --with-lockdir=${localstatedir}/lock \
              --with-piddir=${localstatedir}/run \
              --with-logfilebase=${localstatedir}/log \
              --with-codepagedir=${libdir}/samba \
              --with-swatdir=${datadir}/swat \
              --with-included-iniparser \
              --with-included-popt \
              --with-sendfile-support \
              --without-acl-support \
              --without-cluster-support \
              --without-ads \
              --with-pam \
              --without-krb5 \
              --without-ldap \
              --without-winbind \
              --without-libtdb \
              --without-libnetapi \
              --with-libsmbclient \
              --without-libsmbsharemodes \
              --without-libtevent \
              --without-libaddns \
              --with-shared-modules=pdb_tdbsam,pdb_wbc_sam,idmap_nss,nss_info_template,auth_winbind,auth_wbc,auth_domain \
              ac_cv_path_PYTHON=/not/exist \
              ac_cv_path_PYTHON_CONFIG=/not/exist \
              ac_cv_file__proc_sys_kernel_core_pattern=yes \
              libreplace_cv_HAVE_C99_VSNPRINTF=yes \
              libreplace_cv_HAVE_GETADDRINFO=yes \
              libreplace_cv_HAVE_GETTIMEOFDAY_TZ=yes \
              libreplace_cv_HAVE_IFACE_AIX=no \
              libreplace_cv_HAVE_IFACE_GETIFADDRS=yes \
              libreplace_cv_HAVE_IFACE_IFCONF=yes \
              libreplace_cv_HAVE_IPV6=yes \
              libreplace_cv_HAVE_MMAP=yes \
              libreplace_cv_HAVE_SECURE_MKSTEMP=yes \
              libreplace_cv_REPLACE_INET_NTOA=no \
              LINUX_LFS_SUPPORT=yes \
              samba_path_PYTHON="" \
              samba_cv_CC_NEGATIVE_ENUM_VALUES=yes \
              samba_cv_HAVE_GETADDRINFO=yes \
              samba_cv_HAVE_IFACE_GETIFADDRS=yes \
              samba_cv_HAVE_IFACE_IFCONF=yes \
              samba_cv_HAVE_BROKEN_FCNTL64_LOCKS=no \
              samba_cv_HAVE_BROKEN_GETGROUPS=no \
              samba_cv_HAVE_BROKEN_READDIR_NAME=no \
              samba_cv_HAVE_BROKEN_READLINK=no \
              samba_cv_HAVE_DEV64_T=no \
              samba_cv_HAVE_DEVICE_MAJOR_FN=yes \
              samba_cv_HAVE_DEVICE_MINOR_FN=yes \
              samba_cv_HAVE_errwarn=no \
              samba_cv_HAVE_FCNTL_LOCK=yes \
              samba_cv_HAVE_FTRUNCATE_EXTEND=yes \
              samba_cv_HAVE_INO64_T=no \
              samba_cv_HAVE_KERNEL_CHANGE_NOTIFY=no \
              samba_cv_HAVE_KERNEL_OPLOCKS_LINUX=yes \
              samba_cv_HAVE_KERNEL_SHARE_MODES=yes \
              samba_cv_have_longlong=yes \
              samba_cv_HAVE_MAKEDEV=yes \
              samba_cv_HAVE_OFF64_T=no \
              samba_cv_HAVE_qhalt=no \
              samba_cv_have_setresgid=yes \
              samba_cv_have_setresuid=yes \
              samba_cv_HAVE_STRUCT_FLOCK64=yes \
              samba_cv_HAVE_TRUNCATED_SALT=no \
              samba_cv_HAVE_UNSIGNED_CHAR=no \
              samba_cv_HAVE_w2=no \
              samba_cv_HAVE_Wdeclaration_after_statement=yes \
              samba_cv_HAVE_Werror_implicit_function_declaration=yes \
              samba_cv_HAVE_Werror=yes \
              samba_cv_HAVE_WRFILE_KEYTAB=no \
              samba_cv_linux_getgrouplist_ok=no \
              samba_cv_REALPATH_TAKES_NULL=yes \
              samba_cv_SIZEOF_DEV_T=yes \
              samba_cv_SIZEOF_INO_T=yes \
              samba_cv_SIZEOF_OFF_T=yes \
              samba_cv_SIZEOF_TIME_T=no \
              samba_cv_TIME_T_MAX=no \
              samba_cv_USE_SETRESUID=yes \
              samba_cv_USE_SETREUID=yes \
              samba_sys_stat_statvfs64=yes \
              vfsfileid_cv_statfs=yes \
"

PACKAGES =+ "smbfs smbfs-doc ${PN}-base smbclient ${PN}-base-dbg ${PN}-advanced winbind swat libsmbclient"
PACKAGECONFIG[talloc] = "--enable-external-libtalloc --with-libtalloc, --disable-external-libtalloc --without-libtalloc, talloc"

FILES_${PN}-base       = "${sbindir}/samba_multicall ${sbindir}/smbd ${sbindir}/nmbd \
                          ${bindir}/smbpasswd ${sysconfdir}/init.d/samba \
                          ${sysconfdir}/samba/smb.conf ${sysconfdir}/samba/private \
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

CFLAGS      += '-fPIC -DHAVE_IPV6=1 -DMAX_DEBUG_LEVEL=-1 -ffunction-sections -fdata-sections -ltalloc'
LDFLAGS     += "-Wl,--gc-sections"
EXTRA_OEMAKE+= "DYNEXP= PICFLAG= MODULES="

INITSCRIPT_PACKAGES = "${PN}-base"
INITSCRIPT_NAME_${PN}-base = "samba"
INITSCRIPT_PARAMS = "defaults"
CONFFILES_${PN}-base = "${sysconfdir}/samba/smb.conf ${sysconfdir}/samba/private/users.map ${sysconfdir}/samba/private/smbpasswd"

S = "${WORKDIR}/samba-${PV}/source3"

do_configure() {
    ./script/mkversion.sh
    if [ ! -e acinclude.m4 ]; then
        touch aclocal.m4    
        cat aclocal.m4 > acinclude.m4
    fi
    gnu-configize --force
    oe_runconf
}

do_compile () {
#    sed -i 's/Globals\.bUnixExtensions = True;/Globals.bUnixExtensions = False;/' ${S}/param/loadparm.c
    sed -i 's/Globals\.minprotocol = PROTOCOL_CORE;/Globals.minprotocol = PROTOCOL_NT1;/' ${S}/param/loadparm.c
    sed -i 's/Globals\.maxprotocol = PROTOCOL_NT1;/Globals.maxprotocol = PROTOCOL_SMB2;/' ${S}/param/loadparm.c
    base_do_compile
}

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
    
    install -D -m 755 ${WORKDIR}/init.samba ${D}${sysconfdir}/init.d/samba
    install -D -m 644 ${WORKDIR}/smb.conf ${D}${sysconfdir}/samba/smb.conf
    install -D -m 600 ${WORKDIR}/users.map ${D}${sysconfdir}/samba/private/users.map
    install -D -m 600 ${WORKDIR}/smbpasswd ${D}${sysconfdir}/samba/private/smbpasswd
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
}

pkg_postinst_${PN}-base_prepend() {
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

<<<<<<< HEAD
pkg_postinst_${PN}server() {
#!/bin/sh
if type update-rc.d >/dev/null 2>/dev/null; then
	cp -a $D/etc/init.d/upgrade $D/etc/init.d/samba-upgrade
	if [ -n "$D" ]; then
		OPT="-f -r $D"
	else
		OPT="-f"
	fi
	update-rc.d $OPT samba-upgrade defaults
	echo "Samba upgrade will be performed on next system boot!"
fi

}
=======
inherit binary-compress
FILES_COMPRESS_dm800se = "${bindir}/smbclient"
>>>>>>> 70a2be6... [samba] Maintenance release
