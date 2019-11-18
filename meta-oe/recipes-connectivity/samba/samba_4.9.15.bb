HOMEPAGE = "https://www.samba.org/"
SECTION = "console/network"

LICENSE = "GPL-3.0+ & LGPL-3.0+ & GPL-2.0+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504 \
                    file://${COREBASE}/meta/files/common-licenses/LGPL-3.0;md5=bfccfe952269fff2b407dd11f2f3083b \
                    file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6 "

SAMBA_MIRROR = "http://samba.org/samba/ftp"
MIRRORS += "\
${SAMBA_MIRROR}    http://mirror.internode.on.net/pub/samba \n \
${SAMBA_MIRROR}    http://www.mirrorservice.org/sites/ftp.samba.org \n \
"

SRC_URI = "${SAMBA_MIRROR}/stable/samba-${PV}.tar.gz \
           file://001-samba-4.9.15-pam.patch \
           file://002-dnsserver-4.9.15.patch \
           file://010-source3-msgsock-nvram-fix.patch \
           file://011-source4-msgsock-nvram-fix.patch \
           file://101-do-not-check-xsltproc-manpages.patch \
           file://102-samba-4.9.15-unbundle-libbsd.patch \
           file://103-tmsize-overflow-fix.patch \
           file://201-add-config-option-without-valgrind.patch \
           file://202-iconv-4.9.15.patch \
           file://203-netdb_defines.patch \
           file://206-lib-replace-wscript-Avoid-generating-nested-main-fun.patch \
           file://207-glibc_only.patch \
           file://208-talloc_old_kernel_fix.patch \
           file://210-skip-faulty-ldb_match_test.patch \
           file://300-Revert-pam_smbpass-REMOVE-this-PAM-module.patch \
           file://301-Revert-source3-wscript-remove-pam_smbpass-option-as-it-was-removed.patch \
           file://302-dynamically-create-a-samba-account-if-needed.patch \
           file://smb.conf \
           file://smb-user.conf \
           file://smb-branding.conf \
           file://smb-global.conf \
           file://smb-shares.conf \
           file://smb-vmc.samba \
           file://init.samba \
           file://pam.samba \
           file://users.map \
           file://smbpasswd \
           file://volatiles.03_samba \
           "
SRC_URI_append_libc-musl = " \
           file://400-samba-4.10.10-remove-getpwent_r.patch \
           file://401-cmocka-uintptr_t.patch \
           file://402-samba-fix-musl-lib-without-innetgr.patch \
          "

SRC_URI_append_sh4 = " \
           file://209-strtoull.patch \
          "

SRC_URI[md5sum] = "57510c96714fa8ad0dc9f25fa458c7d1"
SRC_URI[sha256sum] = "377102b80b97941bf0d131b828cae8415190e5bdd2928c2e2c954e29f1904496"

UPSTREAM_CHECK_REGEX = "samba\-(?P<pver>4\.9(\.\d+)+).tar.gz"

inherit systemd cpan-base perlnative update-rc.d qemu pythonnative update-alternatives

# remove default added RDEPENDS on perl
RDEPENDS_${PN}_remove = "perl"

DEPENDS += "qemu-native libxslt-native docbook-xsl-stylesheets-native e2fsprogs readline virtual/libiconv zlib popt libpam libtasn1 python"

inherit features_check
REQUIRED_DISTRO_FEATURES = "pam"

DEPENDS_append_libc-musl = " libtirpc"
CFLAGS_append_libc-musl = " -I${STAGING_INCDIR}/tirpc"
LDFLAGS_append_libc-musl = " -ltirpc"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "samba"
INITSCRIPT_PARAMS_${PN} = "defaults"

SYSTEMD_PACKAGES = "${PN} ${PN}-ad-dc winbind"
SYSTEMD_SERVICE_${PN} = "${@bb.utils.contains('DISTRO_FEATURES_BACKFILL_CONSIDERED', 'sysvinit', 'nmb.service smb.service', '', d)}"
SYSTEMD_SERVICE_${PN}-ad-dc = "${@bb.utils.contains('PACKAGECONFIG', 'ad-dc', 'samba.service', '', d)}"
SYSTEMD_SERVICE_winbind = "winbind.service"

# There are prerequisite settings to enable ad-dc, so disable the service by default.
# Reference:
# https://wiki.samba.org/index.php/Setting_up_Samba_as_an_Active_Directory_Domain_Controller
SYSTEMD_AUTO_ENABLE_${PN}-ad-dc = "disable"

#cross_compile cannot use preforked process, since fork process earlier than point subproces.popen
#to cross Popen
export WAF_NO_PREFORK="yes"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'systemd zeroconf', d)} mitkrb5"

RDEPENDS_${PN}-ctdb-tests += "bash util-linux-getopt"

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

# Building the AD (Active Directory) DC (Domain Controller) requires GnuTLS,
# And ad-dc doesn't work with mitkrb5 for versions prior to 4.7.0 according to:
# http://samba.2283325.n4.nabble.com/samba-4-6-6-Unknown-dependency-kdc-in-service-kdc-objlist-td4722096.html
# So the working combination is:
# 1) ad-dc: enable, gnutls: enable, mitkrb5: disable
# 2) ad-dc: disable, gnutls: enable/disable, mitkrb5: enable
#
# We are now at 4.7.0, so take the above with a grain of salt. We do not need to know where
# krb5kdc is unless ad-dc is enabled, but we tell configure anyhow.
#
PACKAGECONFIG[ad-dc] = "--with-experimental-mit-ad-dc,--without-ad-dc,,"
PACKAGECONFIG[gnutls] = "--enable-gnutls,--disable-gnutls,gnutls,"
PACKAGECONFIG[mitkrb5] = "--with-system-mitkrb5 --with-system-mitkdc=/usr/sbin/krb5kdc,,krb5,"

SAMBA4_AUTH_MODULES=""
SAMBA4_IDMAP_MODULES=""
SAMBA4_PDB_MODULES=""
SAMBA4_VFS_MODULES=""
SAMBA4_MODULES="${SAMBA4_AUTH_MODULES},${SAMBA4_IDMAP_MODULES},${SAMBA4_PDB_MODULES}"

SAMBA4_AUTH_MODULES_STATIC="auth_builtin,auth_sam,auth_unix,auth_script"
SAMBA4_IDMAP_MODULES_STATIC=""
SAMBA4_PDB_MODULES_STATIC="pdb_smbpasswd,pdb_tdbsam"
SAMBA4_VFS_MODULES_STATIC="vfs_default,vfs_aio_pthread"
SAMBA4_MODULES_STATIC="${SAMBA4_AUTH_MODULES_STATIC},${SAMBA4_PDB_MODULES_STATIC},${SAMBA4_VFS_MODULES_STATIC}"

# These libraries are supposed to replace others supplied by packages, but decorate the names of
# .so files so there will not be a conflict.  This is not done consistantly, so be very careful
# when adding to this list.
#
SAMBA4_LIBS="talloc,tevent,tevent-util,texpect,tdb,ldb,tdr,cmocka,replace,roken,wind,hx509,asn1,heimbase,hcrypto,krb5,gssapi,heimntlm,hdb,kdc,NONE"

EXTRA_OECONF += "--without-ads \
                 --without-ad-dc \
                 --without-dnsupdate \
                 --without-cluster-support \
                 --without-winbind \
                 --without-profiling-data \
                 --with-configdir=${sysconfdir}/samba \
                 --with-privatedir=${sysconfdir}/samba/private \
                 --with-piddir=${localstatedir}/run \
                 --with-lockdir=${localstatedir}/lib/samba \
                 --with-cachedir=${localstatedir}/lib/samba \
                 --with-sockets-dir=${localstatedir}/run \
                 --with-logfilebase=${localstatedir}/log/samba \
                 --with-modulesdir=${libdir}/samba \
                 --with-static-modules=${SAMBA4_MODULES_STATIC},!DEFAULT,!FORCED \
                 --with-shared-modules=!DEFAULT,!FORCED \
                 --builtin-libraries=talloc,tevent,tevent-util,texpect,tdb,ldb,tdr,cmocka \
                 --bundled-libraries=${SAMBA4_LIBS} \
                 --with-pam --with-pammodulesdir=${base_libdir}/security \
                 --with-pam_smbpass \
                 --nopyc \
                 --nopyo \
                 --disable-iprint \
                 --without-profiling-data \
                 --with-libiconv=${STAGING_DIR_HOST}${prefix} \
                 --disable-cephfs \
                 --disable-fault-handling \
                 --disable-glusterfs \
                 --disable-rpath \
                 --enable-fhs \
                 --without-regedit \
                 --without-quotas \
                 --disable-python \
                 --without-json \
                 --tests="" \
                "

LDFLAGS += "-Wl,-z,relro,-z,now ${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', ' -fuse-ld=bfd ', '', d)}"

# avoids build breaks when using no-static-libs.inc
DISABLE_STATIC = ""

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

# Three methods for waf cross compile:
# 1. answers:
#    Only --cross-answers - try the cross-answers file, and if
#    there's no corresponding answer, add to the file and mark
#    the configure process as unfinished.
# 2. exec:
#    Only --cross-execute - get the answer from cross-execute,
#    an emulator (qemu) is used to run cross-compiled binaries.
# 3. both:
#    (notes: not supported in lower version of some packages,
#     please check buildtools/wafsamba/samba_cross.py in the
#     package source)
#    Try the cross-answers file first, and if there is no
#    corresponding answer, use cross-execute to get an answer,
#    and add that answer to the file.
#
# The first one is preferred since it may fail with 2 or 3 if
# the target board is not suported by qemu, but we can use 2 or 3
# to help generate the cross answer when adding new board support.
CROSS_METHOD ?= "answer"

do_configure() {
    perl -i -pe 's#lp_private_dir#lp_pid_directory#' ${S}/source3/lib/messages.c

    # Prepare the cross-answers file
    WAF_CROSS_ANSWERS_PATH="${THISDIR}/waf-cross-answers"
    CROSS_ANSWERS="${B}/cross-answers-${TARGET_ARCH}.txt"
    if [ -e ${CROSS_ANSWERS} ]; then
        rm -f ${CROSS_ANSWERS}
    fi
    echo 'Checking uname machine type: "${TARGET_ARCH}"' >> ${CROSS_ANSWERS}
    echo 'Checking uname release type: "${OLDEST_KERNEL}"' >> ${CROSS_ANSWERS}
    cat ${WAF_CROSS_ANSWERS_PATH}/cross-answers-${TARGET_ARCH}.txt >> ${CROSS_ANSWERS}

    qemu_binary="${@qemu_target_binary(d)}"
    if [ "${qemu_binary}" = "qemu-allarch" ]; then
        qemu_binary="qemuwrapper"
    fi

    libdir_qemu="${STAGING_DIR_HOST}/${libdir}"
    base_libdir_qemu="${STAGING_DIR_HOST}/${base_libdir}"

    CROSS_EXEC="${qemu_binary} \
                ${QEMU_OPTIONS} \
                -L ${STAGING_DIR_HOST} \
                -E LD_LIBRARY_PATH=${libdir_qemu}:${base_libdir_qemu}"

    export BUILD_ARCH=${BUILD_ARCH}
    export HOST_ARCH=${HOST_ARCH}
    export STAGING_LIBDIR=${STAGING_LIBDIR}
    export STAGING_INCDIR=${STAGING_INCDIR}
    export PYTHONPATH=${STAGING_DIR_HOST}${PYTHON_SITEPACKAGES_DIR}

    CONFIG_CMD="./configure ${CONFIGUREOPTS} ${EXTRA_OECONF} --cross-compile"
    if [ "${CROSS_METHOD}" = "answer" ]; then
        ${CONFIG_CMD} --cross-answers="${CROSS_ANSWERS}"
    elif [ "${CROSS_METHOD}" = "exec" ]; then
        ${CONFIG_CMD} --cross-exec="${CROSS_EXEC}"
    elif [ "${CROSS_METHOD}" = "both" ]; then
        ${CONFIG_CMD} --cross-answers="${CROSS_ANSWERS}" --cross-exec="${CROSS_EXEC}"
    else
        echo "ERROR: ${CROSS_METHOD} is not valid for cross-compile!"
        exit 1
    fi
}

do_compile[progress] = "outof:^\[\s*(\d+)/\s*(\d+)\]\s+"
do_compile () {
    python ./buildtools/bin/waf ${@oe.utils.parallel_make_argument(d, '-j%d', limit=64)}
}

do_install() {
    oe_runmake install DESTDIR=${D}

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
    rm ${D}${sbindir}/samba-gpupdate
    rm ${D}${bindir}/findsmb
    rm -rf ${D}/var/lib/samba/bind-dns
}

PACKAGES =+ "smbclient ${PN}-common \
             ${PN}-admin ${PN}-utils \
             ${PN}-ldb-tools ${PN}-tdb-tools ${PN}-pdb-tools registry-tools \
             winbind ${PN}-ad-dc ${PN}-ctdb-tests \
             ${PN}-python ${PN}-pidl ${PN}-dsdb-modules ${PN}-testsuite \
            "

#        d.appendVar('RRECOMMENDS_%s' % pn, ' %s' % pkg)

python samba_populate_packages() {
    def module_hook(file, pkg, pattern, format, basename):
        pn = d.getVar('PN')

    mlprefix = d.getVar('MLPREFIX') or ''
    pam_libdir = d.expand('${base_libdir}/security')
    pam_pkgname = mlprefix + 'pam-plugin%s'
    do_split_packages(d, pam_libdir, '^pam_(.*)\.so$', pam_pkgname, 'PAM plugin for %s', extra_depends='', prepend=False)

    libdir = d.getVar('libdir')
    do_split_packages(d, libdir, '^lib(.*)\.so\..*$', 'lib%s', 'Samba %s library', extra_depends='${PN}-common', prepend=False, allow_links=True)
    pkglibdir = '%s/samba' % libdir
    do_split_packages(d, pkglibdir, '^lib(.*)\.so$', 'lib%s', 'Samba %s library', extra_depends='${PN}-common', prepend=False)
    moduledir = '%s/samba/auth' % libdir
    do_split_packages(d, moduledir, '^(.*)\.so$', 'samba-auth-%s', 'Samba %s authentication backend', hook=module_hook, extra_depends='', prepend=False)
    moduledir = '%s/samba/pdb' % libdir
    do_split_packages(d, moduledir, '^(.*)\.so$', 'samba-pdb-%s', 'Samba %s password backend', hook=module_hook, extra_depends='', prepend=False)
}

PACKAGESPLITFUNCS_prepend = "samba_populate_packages "
PACKAGES_DYNAMIC = "samba-auth-.* samba-pdb-.*"

# Common to client and server installation
FILES_${BPN}-common = "${sysconfdir}/default \
                       ${sysconfdir}/pam.d/samba \
                       ${sysconfdir}/samba \
                       ${sysconfdir}/tmpfiles.d \
                       ${localstatedir}/lib/samba \
                       ${localstatedir}/spool/samba \
                       \
                       /lib/security/pam_smbpass.so \
                      "

CONFFILES_${BPN}-common = "${sysconfdir}/pam.d/samba \
                           ${sysconfdir}/samba/smb-user.conf \
                           ${sysconfdir}/samba/private/users.map \
                           ${sysconfdir}/samba/private/smbpasswd \
                          "
INSANE_SKIP_${BPN}-common += "dev-so"

# The Samba client
FILES_smbclient = "${bindir}/smbclient"
RDEPENDS_smbclient += "${BPN}-common"

# The Samba server
FILES_${PN} = " \
               ${sbindir}/smbd \
               ${sbindir}/nmbd \
               ${bindir}/smbcontrol \
               ${bindir}/smbstatus \
               ${sysconfdir}/init.d \
               ${systemd_system_unitdir}/nmb.service \
               ${systemd_system_unitdir}/smb.service \
               ${libdir}/charset/*.so \
               ${libdir}/*.dat \
               ${libdir}/auth/*.so \
               ${bindir}/smbpasswd \
               ${bindir}/testparm \
               \
              "
RDEPENDS_${PN} += "${BPN}-common wsdd"
INSANE_SKIP_${PN} += "dev-so"

# Server admin tools that are not needed in our image
FILES_${PN}-admin = "${bindir}/net \
                     ${bindir}/profiles \
                     ${bindir}/rpcclient \
                     ${bindir}/sharesec \
                     ${bindir}/smbcacls \
                     ${bindir}/smbcquotas \
                     ${sbindir}/eventlogadm \
                    "

# Advanced utilities, generally not needed in our image
FILES_${PN}-utils = "${bindir}/cifsdd \
                     ${bindir}/smbtree \
                     ${bindir}/smbget \
                     ${bindir}/smbspool \
                     ${bindir}/smbtar \
                     ${bindir}/mvxattr \
                     ${bindir}/nmblookup \
                    "

FILES_${PN}-ad-dc = "${sbindir}/samba \
                     ${systemd_system_unitdir}/samba.service \
                     ${libdir}/krb5/plugins/kdb/samba.so \
"
RDEPENDS_${PN}-ad-dc += "samba krb5-kdc"

FILES_registry-tools = "${bindir}/regdiff \
                        ${bindir}/regpatch \
                        ${bindir}/regshell \
                        ${bindir}/regtree"

FILES_${PN}-ldb-tools = "${bindir}/ldbadd \
                         ${bindir}/ldbdel \
                         ${bindir}/ldbedit \
                         ${bindir}/ldbmodify \
                         ${bindir}/ldbrename \
                         ${bindir}/ldbsearch \
                         ${bindir}/oLschema2ldif \
                        "

FILES_${PN}-pdb-tools = "${bindir}/pdbedit"

ALTERNATIVE_PRIORITY = "100"
ALTERNATIVE_${PN}-tdb-tools = "dbwrap_tool tdbbackup tdbdump tdbrestore tdbtool"

ALTERNATIVE_LINK_NAME[dbwrap_tool] = "${bindir}/dbwrap_tool"
ALTERNATIVE_LINK_NAME[tdbbackup]   = "${bindir}/tdbbackup"
ALTERNATIVE_LINK_NAME[tdbdump]     = "${bindir}/tdbdump"
ALTERNATIVE_LINK_NAME[tdbrestore]  = "${bindir}/tdbrestore"
ALTERNATIVE_LINK_NAME[tdbtool]     = "${bindir}/tdbtool"

FILES_${PN}-tdb-tools = "${bindir}/dbwrap_tool \
                         ${bindir}/tdbbackup \
                         ${bindir}/tdbdump \
                         ${bindir}/tdbrestore \
                         ${bindir}/tdbtool \
                        "

FILES_winbind = "${sbindir}/winbindd \
                 ${bindir}/wbinfo \
                 ${bindir}/ntlm_auth \
                 ${libdir}/samba/idmap \
                 ${libdir}/samba/nss_info \
                 ${libdir}/samba/krb5/winbind_krb5_locator.so \
                 ${libdir}/samba/krb5/winbind_krb5_localauth.so \
                 ${sysconfdir}/init.d/winbind \
                 ${systemd_system_unitdir}/winbind.service \
                "

FILES_${PN}-ctdb-tests = "${bindir}/ctdb_run_tests \
                          ${bindir}/ctdb_run_cluster_tests \
                          ${sysconfdir}/ctdb/nodes \
                          ${datadir}/ctdb-tests \
                          ${datadir}/ctdb/tests \
                          ${localstatedir}/lib/ctdb \
                         "

FILES_${PN}-dsdb-modules = "${libdir}/samba/ldb"

FILES_${PN}-testsuite = "${bindir}/gentest \
                         ${bindir}/locktest \
                         ${bindir}/masktest \
                         ${bindir}/ndrdump \
                         ${bindir}/smbtorture"

FILES_${PN}-python = "${PYTHON_SITEPACKAGES_DIR}"

RDEPENDS_${PN}-pidl_append = " perl"
FILES_${PN}-pidl = "${bindir}/pidl ${datadir}/perl5/Parse"

pkg_postinst_${BPN}-common_append() {
#!/bin/sh

set +e
grep -v 'pam_smbpass.so' $D/etc/pam.d/common-password > $D/tmp/common-password
mv $D/tmp/common-password $D/etc/pam.d/common-password
echo -e "password\toptional\t\t\tpam_smbpass.so use_authtok use_first_pass" >> $D/etc/pam.d/common-password

grep -qE '^kids:' $D/etc/passwd
if [[ $? -ne 0 ]] ; then
    echo 'kids:x:500:500:Linux User,,,:/media:/bin/false' >> $D/etc/passwd
    echo 'kids:!:16560:0:99999:7:::' >> $D/etc/shadow
fi

if [ -e $D/etc/samba/distro/smb-vmc.vmc ]; then
    rm $D/etc/samba/distro/smb-vmc.conf 2>/dev/null || true
    ln -s smb-vmc.vmc $D/etc/samba/distro/smb-vmc.conf
else
    rm $D/etc/samba/distro/smb-vmc.conf 2>/dev/null || true
    ln -s smb-vmc.samba $D/etc/samba/distro/smb-vmc.conf
fi

if [ -z "$D" ]; then
    set +e
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

pkg_prerm_${BPN}-common_prepend() {
#!/bin/sh
grep -v 'pam_smbpass.so' $D/etc/pam.d/common-password > $D/tmp/common-password
mv $D/tmp/common-password $D/etc/pam.d/common-password
}

pkg_postrm_${BPN}-common_prepend() {
#!/bin/sh
rm $D/etc/samba/distro/smb-vmc.conf 2>/dev/null || true
}
