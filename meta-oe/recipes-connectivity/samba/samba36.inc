LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://../COPYING;md5=d32239bcb673463ab874e80d47fae504"

SECTION = "console/network"
DEPENDS = "readline virtual/libiconv zlib popt libpam libtalloc attr avahi libtirpc"

inherit autotools-brokensep

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
           file://glibc_major.patch;patchdir=.. \
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

PACKAGECONFIG[talloc] = "--enable-external-libtalloc --with-libtalloc, --disable-external-libtalloc --without-libtalloc, talloc"

CFLAGS      += '-fPIC -DHAVE_IPV6=1 -DMAX_DEBUG_LEVEL=-1 -ffunction-sections -fdata-sections -ltalloc -I${STAGING_INCDIR}/tirpc'
LDFLAGS     += "-Wl,--gc-sections -ltirpc"
EXTRA_OEMAKE+= "DYNEXP= PICFLAG= MODULES="

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
    sed -i 's/Globals\.minprotocol = PROTOCOL_CORE;/Globals.minprotocol = PROTOCOL_NT1;/' ${S}/param/loadparm.c
    sed -i 's/Globals\.maxprotocol = PROTOCOL_NT1;/Globals.maxprotocol = PROTOCOL_SMB2;/' ${S}/param/loadparm.c
    base_do_compile
}
