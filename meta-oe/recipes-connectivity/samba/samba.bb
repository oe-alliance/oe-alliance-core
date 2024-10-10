PR = "r3"

require samba-source.inc
require samba-waf.inc

#inherit cpan-base perlnative python3native pkgconfig
inherit  cpan-base perlnative perl-version pkgconfig

DEPENDS += "asn1compile-native libparse-yapp-perl-native bison-native qemu-native libxslt-native docbook-xsl-stylesheets-native e2fsprogs jansson readline zlib popt gnutls libtalloc libtasn1"

inherit features_check
REQUIRED_DISTRO_FEATURES = "pam"

DEPENDS:append:libc-musl = " libtirpc"
CFLAGS:append:libc-musl = " -I${STAGING_INCDIR}/tirpc"
LDFLAGS:append:libc-musl = " -ltirpc"

PACKAGECONFIG ?= "${@bb.utils.filter('DISTRO_FEATURES', 'systemd zeroconf', d)} netbios"

# with ad-dc:
#    --bundled-libraries=talloc,tevent,tevent-util,texpect,tdb,ldb,tdr,cmocka,replace,pytalloc-util,pyldb-util,roken,wind,hx509,asn1,heimbase,hcrypto,krb5,gssapi,heimntlm,hdb,kdc,NONE

# without ad-dc:
#    --bundled-libraries=talloc,tevent,tevent-util,texpect,tdb,ldb,tdr,cmocka,replace,roken,wind,hx509,asn1,heimbase,hcrypto,krb5,gssapi,heimntlm,hdb,kdc,NONE

PACKAGECONFIG[acl] = "--with-acl-support,--without-acl-support,acl"
PACKAGECONFIG[fam] = "--with-fam,--without-fam,gamin"
PACKAGECONFIG[cups] = "--enable-cups,--disable-cups,cups"
PACKAGECONFIG[ldap] = "--with-ldap,--without-ldap,openldap"
PACKAGECONFIG[sasl] = ",,cyrus-sasl"
PACKAGECONFIG[systemd] = "--with-systemd,--without-systemd,systemd"
PACKAGECONFIG[dmapi] = "--with-dmapi,--without-dmapi,dmapi"
PACKAGECONFIG[zeroconf] = "--enable-avahi,--disable-avahi,avahi"
PACKAGECONFIG[valgrind] = ",--without-valgrind,valgrind,"
PACKAGECONFIG[lttng] = "--with-lttng, --without-lttng,lttng-ust"
PACKAGECONFIG[archive] = "--with-libarchive, --without-libarchive, libarchive"
PACKAGECONFIG[libunwind] = ", , libunwind"
PACKAGECONFIG[gpgme] = ",--without-gpgme,,"
PACKAGECONFIG[lmdb] = ",--without-ldb-lmdb,lmdb,"
PACKAGECONFIG[libbsd] = "--with-libbsd, --without-libbsd, libbsd"
PACKAGECONFIG[ad-dc] = "--with-experimental-mit-ad-dc,--without-ad-dc --without-ads --without-json --disable-python --nopyc --nopyo,python3-markdown python3-dnspython,"
PACKAGECONFIG[mitkrb5] = "--with-system-mitkrb5 --with-system-mitkdc=/usr/sbin/krb5kdc,,krb5,"
PACKAGECONFIG[netbios] = " "

SAMBA4_AUTH_MODULES_STATIC="auth_builtin,auth_sam,auth_unix"
SAMBA4_AUTH_MODULES_SHARED="auth_script"
SAMBA4_PDB_MODULES_STATIC="pdb_smbpasswd,pdb_tdbsam"
SAMBA4_VFS_MODULES_STATIC="vfs_default"
SAMBA4_VFS_MODULES_SHARED="vfs_widelinks"
SAMBA4_MODULES_STATIC="${SAMBA4_AUTH_MODULES_STATIC},${SAMBA4_PDB_MODULES_STATIC},${SAMBA4_VFS_MODULES_STATIC}"
SAMBA4_MODULES_SHARED="${SAMBA4_AUTH_MODULES_SHARED},${SAMBA4_VFS_MODULES_SHARED}"

# These libraries are supposed to replace others supplied by packages, but decorate the names of
# .so files so there will not be a conflict.  This is not done consistantly, so be very careful
# when adding to this list.
#
#SAMBA4_LIBS="tevent,tevent-util,texpect,tdb,ldb,tdr,cmocka,replace,roken,wind,hx509,asn1,heimbase,hcrypto,krb5,gssapi,heimntlm,hdb,kdc,NONE"

EXTRA_OECONF += "--disable-cups \
                 --disable-iprint \
                 --disable-cephfs \
                 --disable-fault-handling \
                 --disable-glusterfs \
                 --disable-rpath \
                 --disable-rpath-install \
                 --disable-rpath-private-install \
                 --disable-spotlight \
                 --enable-fhs \
                 --without-automount \
                 --without-lttng \
                 --without-utmp \
                 --without-dmapi \
                 --without-fam \
                 --without-gettext \
                 --without-regedit \
                 --without-gpgme \
                 --without-pie \
                 --with-configdir=${sysconfdir}/samba \
                 --with-privatedir=${sysconfdir}/samba/private \
                 --with-piddir=${localstatedir}/run \
                 --with-lockdir=${@bb.utils.contains('IMAGE_FSTYPES','jffs2','${localstatedir}/run/samba','${localstatedir}/lib/samba',d)} \
                 --with-cachedir=${localstatedir}/lib/samba \
                 --with-sockets-dir=${localstatedir}/run \
                 --with-logfilebase=${localstatedir}/log/samba \
                 --with-modulesdir=${libdir} \
                 --with-static-modules=${SAMBA4_MODULES_STATIC},!DEFAULT,!FORCED \
                 --with-shared-modules=${SAMBA4_MODULES_SHARED},!DEFAULT,!FORCED \
                 --bundled-libraries=NONE,tevent,tevent-util,texpect,tdb,ldb,tdr,cmocka,replace,roken,wind,hx509,asn1,heimbase,hcrypto,krb5,gssapi,heimntlm,hdb,kdc,!asn1_compile,!compile_et \
                 --private-libraries=tevent,tevent-util,texpect,tdb,ldb,tdr,cmocka,replace \
                 --with-pam --with-pammodulesdir=${base_libdir}/security \
                 --with-pam_smbpass \
                "

LDFLAGS += "-Wl,-z,relro,-z,now ${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', ' -fuse-ld=bfd ', '', d)}"

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



do_configure:prepend () {
    # un-bundle dnspython
    sed '/"dns.resolver":/d' ${S}/third_party/wscript
    # unbundle iso8601
    sed '/"iso8601":/d' ${S}/third_party/wscript
}

do_configure:append() {
    cd ${S}/pidl/
    perl Makefile.PL PREFIX=${prefix}
    sed -e 's,VENDORPREFIX)/lib/perl,VENDORPREFIX)/${baselib}/perl,g' \
        -e 's,PERLPREFIX)/lib/perl,PERLPREFIX)/${baselib}/perl,g' -i Makefile
}

do_compile[progress] = "outof:^\[\s*(\d+)/\s*(\d+)\]\s+"

do_compile() {
    sed -e '/#define HAVE_ICU_I18N 1/d' -e '/#define HAVE_LIBICUI.* 1/d' -i ${S}/bin/default/include/config.h

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

    TARGETS=$SAMBA4_MODULES_STATIC,$SAMBA4_MODULES,$BUILD_TARGETS_SERVER,$BUILD_TARGETS_CLIENT,$BUILD_TARGETS_UTILS,$BUILD_TARGETS_ADMIN

    # BUG: waf can not handle ",," in targets
    TARGETS=$(echo $TARGETS | sed s/,,*/,/g)
    # remove leading comma
    TARGETS=$(echo $TARGETS | sed 's/^,//')

    ./buildtools/bin/waf --targets=$TARGETS ${@oe.utils.parallel_make_argument(d, '--jobs=%d', limit=64)}
    oe_runmake -C ${S}/pidl
}

do_install() {
    oe_runmake install DESTDIR=${D}

    [[ -e ${D}/${libdir}/samba ]] && mv ${D}/${libdir}/samba/* ${D}/${libdir}/
    [[ -e ${D}/${libdir}/samba ]] && rm -rf ${D}/${libdir}/samba

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

    install -d ${D}${sysconfdir}/tmpfiles.d
    install -m 644 packaging/systemd/samba.conf.tmp ${D}${sysconfdir}/tmpfiles.d/samba.conf
    echo "d ${localstatedir}/log/samba 0755 root root -" \
        >> ${D}${sysconfdir}/tmpfiles.d/samba.conf

    install -D -m 644 ${UNPACKDIR}/volatiles.03_samba ${D}${sysconfdir}/default/volatiles/03_samba

    # fix file-rdeps qa warning
    if [ -f ${D}${bindir}/onnode ]; then
        sed -i 's:\(#!/bin/\)bash:\1sh:' ${D}${bindir}/onnode
    fi

    if ${@bb.utils.contains('IMAGE_FSTYPES','jffs2','true','false',d)}; then
        rm -rf ${D}${localstatedir}/run/samba
    fi

    rm -rf ${D}/run ${D}${localstatedir}/run ${D}${localstatedir}/log

    install -d ${D}${sysconfdir}/pam.d
    install -m 644 ${UNPACKDIR}/pam.samba ${D}${sysconfdir}/pam.d/samba

    install -d ${D}${sysconfdir}/samba
    install -d ${D}${sysconfdir}/samba/private
    echo "127.0.0.1 localhost" > ${D}${sysconfdir}/samba/lmhosts
    install -m 644 ${UNPACKDIR}/smb.conf ${D}${sysconfdir}/samba
    install -m 644 ${UNPACKDIR}/smb-user.conf ${D}${sysconfdir}/samba

    if ${@bb.utils.contains_any('DISTRO_NAME','openatv openvix','true','false',d)}; then
        install -m 644 ${UNPACKDIR}/smb-local.conf ${D}${sysconfdir}/samba
    else
        install -d ${D}${sysconfdir}/samba/distro
        install -m 644 ${UNPACKDIR}/smb-branding.conf ${D}${sysconfdir}/samba/distro
        install -m 644 ${UNPACKDIR}/smb-global.conf ${D}${sysconfdir}/samba/distro
        install -m 644 ${UNPACKDIR}/smb-shares.conf ${D}${sysconfdir}/samba/distro
        install -m 644 ${UNPACKDIR}/smb-vmc.samba ${D}${sysconfdir}/samba/distro
    fi 
    install -m 644 ${UNPACKDIR}/smbpasswd ${D}${sysconfdir}/samba/private
    install -m 644 ${UNPACKDIR}/users.map ${D}${sysconfdir}/samba/private
    install -d ${D}${sysconfdir}/init.d
    install -m 755 ${UNPACKDIR}/init.samba ${D}${sysconfdir}/init.d/samba
    install -m 755 ${UNPACKDIR}/init.wsdd ${D}${sysconfdir}/init.d/wsdd

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        perl -i -pe 's:(PIDFile=)/run/(.*?\.pid):${1}${localstatedir}/run/${2}:' ${D}${systemd_system_unitdir}/*.service
        if ${@bb.utils.contains('DISTRO_FEATURES_BACKFILL_CONSIDERED','sysvinit','true','false',d)}; then
            :
        else
            rm -f ${D}${systemd_system_unitdir}/smb.service
            rm -f ${D}${systemd_system_unitdir}/nmb.service
            rm -f ${D}${systemd_system_unitdir}/samba.service
        fi
    fi
    rm -f ${D}${sbindir}/samba-gpupdate || true
    rm -f ${D}${bindir}/findsmb || true
    rm -rf ${D}/var/lib/samba/bind-dns || true
}

require samba-pkgs.inc

INSANE_SKIP += "dev-so file-rdeps"
INSANE_SKIP:libsmbconf += "file-rdeps"
