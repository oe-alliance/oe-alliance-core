PR = "r3"

require samba-source.inc

inherit cpan-base perlnative pythonnative

DEPENDS += "qemu-native libxslt-native docbook-xsl-stylesheets-native e2fsprogs readline virtual/libiconv zlib popt libpam python asn1compile-native"

inherit distro_features_check
REQUIRED_DISTRO_FEATURES = "pam"

DEPENDS_append_libc-musl = " libtirpc"
CFLAGS_append_libc-musl = " -I${STAGING_INCDIR}/tirpc"
LDFLAGS_append_libc-musl = " -ltirpc"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'systemd zeroconf', d)} netbios"

# with ad-dc:
#    --bundled-libraries=talloc,tevent,tevent-util,texpect,tdb,ldb,tdr,cmocka,replace,pytalloc-util,pyldb-util,roken,wind,hx509,asn1,heimbase,hcrypto,krb5,gssapi,heimntlm,hdb,kdc,NONE 

PACKAGECONFIG[ad-dc] = " \
    --enable-gnutls --with-dnsupdate --with-ads --with-ldap \
    , \
    --without-ad-dc --without-json --without-libarchive --disable-python --nopyc --nopyo \
    --disable-gnutls --without-dnsupdate --without-ads --without-ldap \
    , \
    gnutls libarchive openldap \
   "

#without ad-dc:
#    --bundled-libraries=talloc,tevent,tevent-util,texpect,tdb,ldb,tdr,cmocka,replace,roken,wind,hx509,asn1,heimbase,hcrypto,krb5,gssapi,heimntlm,hdb,kdc,NONE
PACKAGECONFIG[acl] = "--with-acl-support,--without-acl-support,acl"
PACKAGECONFIG[systemd] = "--with-systemd,--without-systemd,systemd"
PACKAGECONFIG[zeroconf] = "--enable-avahi,--disable-avahi,avahi"
PACKAGECONFIG[quotas] = "--with-quotas,--without-quotas,"
PACKAGECONFIG[winbind] = "--with-winbind,--without-winbind,"
PACKAGECONFIG[valgrind] = ",--without-valgrind,valgrind"
PACKAGECONFIG[libunwind] = ", , libunwind"
PACKAGECONFIG[lmdb] = ",--without-ldb-lmdb,lmdb,"

SAMBA4_AUTH_MODULES=""
SAMBA4_IDMAP_MODULES=""
SAMBA4_PDB_MODULES=""
SAMBA4_VFS_MODULES=""
SAMBA4_MODULES="${SAMBA4_AUTH_MODULES},${SAMBA4_IDMAP_MODULES},${SAMBA4_PDB_MODULES}"

SAMBA4_AUTH_MODULES_STATIC="auth_builtin,auth_sam,auth_unix,auth_script"
SAMBA4_IDMAP_MODULES_STATIC=""
SAMBA4_PDB_MODULES_STATIC="pdb_smbpasswd,pdb_tdbsam"
SAMBA4_VFS_MODULES_STATIC="vfs_default"
SAMBA4_MODULES_STATIC="${SAMBA4_AUTH_MODULES_STATIC},${SAMBA4_PDB_MODULES_STATIC},${SAMBA4_VFS_MODULES_STATIC}"

# These libraries are supposed to replace others supplied by packages, but decorate the names of
# .so files so there will not be a conflict.  This is not done consistantly, so be very careful
# when adding to this list.
#
#SAMBA4_LIBS="talloc,tevent,tevent-util,texpect,tdb,ldb,tdr,cmocka,replace,roken,wind,hx509,asn1,heimbase,hcrypto,krb5,gssapi,heimntlm,hdb,kdc,NONE"

EXTRA_OECONF += "--disable-cups \
                 --disable-iprint \
                 --disable-cephfs \
                 --disable-fault-handling \
                 --disable-glusterfs \
                 --disable-rpath \
                 --disable-rpath-install \
                 --disable-rpath-private-install \
                 --enable-fhs \
                 --without-automount \
                 --without-iconv \
                 --without-lttng \
                 --without-ntvfs-fileserver \
                 --without-utmp \
                 --without-dmapi \
                 --without-fam \
                 --without-gettext \
                 --without-regedit \
                 --without-gpgme \
                 --with-configdir=${sysconfdir}/samba \
                 --with-privatedir=${sysconfdir}/samba/private \
                 --with-piddir=${localstatedir}/run \
                 --with-lockdir=${@bb.utils.contains('IMAGE_FSTYPES','jffs2','${localstatedir}/run/samba','${localstatedir}/lib/samba',d)} \
                 --with-cachedir=${localstatedir}/lib/samba \
                 --with-sockets-dir=${localstatedir}/run \
                 --with-logfilebase=${localstatedir}/log/samba \
                 --with-modulesdir=${libdir} \
                 --bundled-libraries=talloc,tevent,tevent-util,texpect,tdb,ldb,tdr,cmocka,replace,roken,wind,hx509,asn1,heimbase,hcrypto,krb5,gssapi,heimntlm,hdb,kdc,NONE \
                 --with-static-modules=${SAMBA4_MODULES_STATIC},!DEFAULT,!FORCED \
                 --with-shared-modules=!DEFAULT,!FORCED \
                 --builtin-libraries=talloc,tevent,tevent-util,texpect,tdb,ldb,tdr,cmocka \
                 --private-libraries=talloc,tevent,tevent-util,texpect,tdb,ldb,tdr,cmocka,replace \
                 --with-pam --with-pammodulesdir=${base_libdir}/security \
                 --with-pam_smbpass \
                 --accel-aes=none \
                "

LDFLAGS += "-Wl,-z,relro,-z,now ${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', ' -fuse-ld=bfd ', '', d)}"

# avoids build breaks when using no-static-libs.inc
#DISABLE_STATIC = ""

CONFIGUREOPTS = " --prefix=${prefix} \
                  --bindir=${bindir} \
                  --sbindir=${sbindir} \
                  --libexecdir=${libexecdir} \
                  --datadir=${datadir} \
                  --sysconfdir=${sysconfdir} \
                  --sharedstatedir=${sharedstatedir} \
                  --localstatedir=${localstatedir} \
                  --libdir=${libdir} \
                  --includedir=${includedir} \
                  --oldincludedir=${oldincludedir} \
                  --infodir=${infodir} \
                  --mandir=${mandir} \
                  ${PACKAGECONFIG_CONFARGS} \
                "

require samba-waf.inc

do_configure_prepend () {
    # un-bundle dnspython
    sed '/"dns.resolver":/d' ${S}/third_party/wscript
    # unbundle iso8601
    sed '/"iso8601":/d' ${S}/third_party/wscript
}


do_compile[progress] = "outof:^\[\s*(\d+)/\s*(\d+)\]\s+"
# BUG: We need to use "waf install --targets=" otherwise a "make install" or "waf install" will retrigger a full recompile of all possible targets!

# Yocto BUG: Yocto doesn't know D during compile and it kills it before do_install anyways
# Workaround: Use some intermediate directory
do_compile () {
    # CONFIG_PACKAGE_samba4-server
    BUILD_TARGETS_SERVER=smbd/smbd,smbpasswd,testparm,smbstatus,smbcontrol,pamsmbpass
    # Optional server targets
    if [ "${@bb.utils.contains('PACKAGECONFIG', 'acl', 'yes', 'no', d)}" = "yes" ]; then
        BUILD_TARGETS_SERVER=$BUILD_TARGETS_SERVER,sharesec
    fi
    if [ "${@bb.utils.contains('PACKAGECONFIG', 'netbios', 'yes', 'no', d)}" = "yes" ]; then
        BUILD_TARGETS_SERVER=$BUILD_TARGETS_SERVER,nmbd
    fi
    if [ "${@bb.utils.contains('PACKAGECONFIG', 'ad-dc', 'yes', 'no', d)}" = "yes" ]; then
        BUILD_TARGETS_SERVER=$BUILD_TARGETS_SERVER,samba,nsstest,ntlm_auth,samba4kinit,samba4kgetcred,samba4kpasswd,samba4ktutil
    fi
    if [ "${@bb.utils.contains('PACKAGECONFIG', 'winbind', 'yes', 'no', d)}" = "yes" ]; then
        BUILD_TARGETS_SERVER=$BUILD_TARGETS_SERVER,winbindd,wbinfo,winbind_krb5_locator
    fi
    # CONFIG_PACKAGE_samba4-client
    BUILD_TARGETS_CLIENT=client/smbclient
    # CONFIG_PACKAGE_samba4-admin
    BUILD_TARGETS_ADMIN=net,profiles,rpcclient,smbcacls,smbcquotas,eventlogadm
    # CONFIG_PACKAGE_samba4-utils
    BUILD_TARGETS_UTILS=smbtree,smbget,mvxattr,nmblookup,pdbedit

    TARGETS=${SAMBA4_MODULES_STATIC},${SAMBA4_MODULES},$BUILD_TARGETS_SERVER,$BUILD_TARGETS_CLIENT,$BUILD_TARGETS_UTILS,$BUILD_TARGETS_ADMIN

    # BUG: waf can not handle ",," in targets
    TARGETS=$(echo $TARGETS | sed s/,,*/,/g)

    rm -rf ${S}/../compiled || true
    mkdir -p ${S}/../compiled || true
    ./buildtools/bin/waf install ${@oe.utils.parallel_make_argument(d, '--jobs=%d', limit=64)} --destdir=${S}/../compiled --targets=$TARGETS
}

do_install() {
    mkdir -p ${D}
    cp -R --no-dereference --preserve=mode,timestamps,links,xattr ${S}/../compiled/* ${D}/ || true

    [[ -e ${D}/${libdir}/samba ]] && mv ${D}/${libdir}/samba/* ${D}/${libdir}/
    [[ -e ${D}/${libdir}/samba ]] && rm -r ${D}/${libdir}/samba

    for section in 1 5 7; do
        install -d ${D}${mandir}/man$section
        install -m 0644 ctdb/doc/*.$section ${D}${mandir}/man$section
    done
    for section in 1 5 7 8; do
        install -d ${D}${mandir}/man$section
        install -m 0644 docs/manpages/*.$section ${D}${mandir}/man$section
    done

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/bin/default/packaging/systemd/*.service ${D}${systemd_system_unitdir}/
    sed -e 's,\(ExecReload=\).*\(/kill\),\1${base_bindir}\2,' \
        -e 's,/etc/sysconfig/samba,${sysconfdir}/default/samba,' \
        -i ${D}${systemd_system_unitdir}/*.service

    if [ "${@bb.utils.contains('PACKAGECONFIG', 'ad-dc', 'yes', 'no', d)}" = "no" ]; then
        rm -f ${D}${systemd_system_unitdir}/samba.service
    fi

    install -D -m 644 ${WORKDIR}/volatiles.03_samba ${D}${sysconfdir}/default/volatiles/03_samba

    # fix file-rdeps qa warning
    if [ -f ${D}${bindir}/onnode ]; then
        sed -i 's:\(#!/bin/\)bash:\1sh:' ${D}${bindir}/onnode
    fi

    if ${@bb.utils.contains('IMAGE_FSTYPES','jffs2','true','false',d)}; then
		rm -rf ${D}${localstatedir}/run/samba
    fi

    rm -rf ${D}/run ${D}${localstatedir}/run ${D}${localstatedir}/log
    
    install -d ${D}${sysconfdir}/pam.d
    install -m 644 ${WORKDIR}/pam.samba ${D}${sysconfdir}/pam.d/samba

    install -d ${D}${sysconfdir}/samba
    install -d ${D}${sysconfdir}/samba/distro
    install -d ${D}${sysconfdir}/samba/private
    echo "127.0.0.1 localhost" > ${D}${sysconfdir}/samba/lmhosts
    install -m 644 ${WORKDIR}/smb.conf ${D}${sysconfdir}/samba
    install -m 644 ${WORKDIR}/smb-user.conf ${D}${sysconfdir}/samba
    install -m 644 ${WORKDIR}/smb-branding.conf ${D}${sysconfdir}/samba/distro
    install -m 644 ${WORKDIR}/smb-global.conf ${D}${sysconfdir}/samba/distro
    install -m 644 ${WORKDIR}/smb-shares.conf ${D}${sysconfdir}/samba/distro
    install -m 644 ${WORKDIR}/smb-vmc.samba ${D}${sysconfdir}/samba/distro
    install -m 644 ${WORKDIR}/smbpasswd ${D}${sysconfdir}/samba/private
    install -m 644 ${WORKDIR}/users.map ${D}${sysconfdir}/samba/private
    install -d ${D}${sysconfdir}/init.d
    install -m 755 ${WORKDIR}/init.samba ${D}${sysconfdir}/init.d/samba

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        perl -i -pe 's:(PIDFile=)/run/(.*?\.pid):${1}${localstatedir}/run/${2}:' ${D}${systemd_system_unitdir}/*.service
        if ${@bb.utils.contains('DISTRO_FEATURES_BACKFILL_CONSIDERED','sysvinit','true','false',d)}; then
            :
        else
            rm ${D}${systemd_system_unitdir}/smb.service
            rm ${D}${systemd_system_unitdir}/nmb.service
            rm ${D}${systemd_system_unitdir}/samba.service
        fi
    fi 
    rm ${D}${sbindir}/samba-gpupdate || true
    rm ${D}${bindir}/findsmb || true
    rm -rf ${D}/var/lib/samba/bind-dns || true
}

require samba-pkgs.inc

INSANE_SKIP += "dev-so"
