inherit systemd update-rc.d update-alternatives

PACKAGES =+ "smbclient ${PN}-common \
             ${PN}-admin ${PN}-utils \
             ${PN}-ldb-tools ${PN}-tdb-tools ${PN}-libtdb ${PN}-pdb-tools registry-tools \
             winbind libwinbind ${PN}-ad-dc ${PN}-ctdb-tests \
             ${PN}-python ${PN}-pidl ${PN}-dsdb-modules ${PN}-testsuite swat \
            "

# remove default added RDEPENDS on perl
RDEPENDS:${PN}:remove = "perl"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "samba"
INITSCRIPT_PARAMS:${PN} = "defaults"

SYSTEMD_PACKAGES = "${PN} ${PN}-ad-dc winbind"
SYSTEMD_SERVICE:${PN} = "${@bb.utils.contains('DISTRO_FEATURES_BACKFILL_CONSIDERED', 'sysvinit', 'nmb.service smb.service', '', d)}"
SYSTEMD_SERVICE:${PN}-ad-dc = "${@bb.utils.contains('PACKAGECONFIG', 'ad-dc', 'samba.service', '', d)}"
SYSTEMD_SERVICE:winbind = "winbind.service"

# There are prerequisite settings to enable ad-dc, so disable the service by default.
# Reference:
# https://wiki.samba.org/index.php/Setting_up_Samba_as_an_Active_Directory_Domain_Controller
SYSTEMD_AUTO_ENABLE:${PN}-ad-dc = "disable"

RDEPENDS:${PN}-ctdb-tests += "bash util-linux-getopt"

python samba_populate_packages() {
    def module_hook(file, pkg, pattern, format, basename):
        pn = d.getVar('PN')

    mlprefix = d.getVar('MLPREFIX') or ''
    pam_libdir = d.expand('${base_libdir}/security')
    pam_pkgname = mlprefix + 'pam-plugin%s'
    do_split_packages(d, pam_libdir, '^pam_(.*)\.so$', pam_pkgname, 'PAM plugin for %s', extra_depends='', prepend=False)

    pkglibdir = d.getVar('libdir')
    do_split_packages(d, pkglibdir, '^lib(.*)\.so\..*$', 'lib%s', 'Samba %s library', allow_links=True, extra_depends='${PN}-common', prepend=True)
    do_split_packages(d, pkglibdir, '^lib(.*)\.so$', 'lib%s', 'Samba %s library', allow_links=True, extra_depends='${PN}-common', prepend=True)
    moduledir = '%s/samba/auth' % pkglibdir
    do_split_packages(d, moduledir, '^(.*)\.so$', 'samba-auth-%s', 'Samba %s authentication backend', hook=module_hook, extra_depends='', prepend=False)
    moduledir = '%s/samba/pdb' % pkglibdir
    do_split_packages(d, moduledir, '^(.*)\.so$', 'samba-pdb-%s', 'Samba %s password backend', hook=module_hook, extra_depends='', prepend=False)
}

PACKAGESPLITFUNCS:prepend = "samba_populate_packages "
PACKAGES_DYNAMIC = "samba-auth-.* samba-pdb-.*"

# Common to client and server installation
FILES:${BPN}-common = "${sysconfdir}/default \
                       ${sysconfdir}/pam.d/samba \
                       ${sysconfdir}/samba \
                       ${sysconfdir}/tmpfiles.d \
                       ${localstatedir}/lib/samba \
                       ${localstatedir}/spool/samba \
                       ${base_libdir}/security/pam_smbpass.so \
                       "

CONFFILES:${BPN}-common = "${sysconfdir}/pam.d/samba \
                           ${sysconfdir}/samba/smb-user.conf \
                           ${sysconfdir}/samba/private/users.map \
                           ${sysconfdir}/samba/private/smbpasswd \
                          "
INSANE_SKIP:${BPN}-common += "dev-so"

# The Samba client
FILES:smbclient = "${bindir}/smbclient"
RDEPENDS:smbclient += "${BPN}-common"

# The Samba server
FILES:${PN} = " \
               ${sbindir}/smbd \
               ${sbindir}/nmbd \
               ${sbindir}/samba_multicall \
               ${bindir}/smbcontrol \
               ${bindir}/smbstatus \
               ${sysconfdir}/init.d \
               ${systemd_system_unitdir}/nmb.service \
               ${systemd_system_unitdir}/smb.service \
               ${libdir}/charset/*.so \
               ${libdir}/*.dat \
               ${libdir}/samba/*.dat \
               ${libdir}/auth/*.so \
               ${bindir}/smbpasswd \
               ${bindir}/testparm \
               "
RDEPENDS:${PN} += "${BPN}-common wsdd"
INSANE_SKIP:${PN} += "dev-so"

# Server admin tools that are not needed in our image
FILES:${PN}-admin = "${bindir}/net \
                     ${bindir}/profiles \
                     ${bindir}/rpcclient \
                     ${bindir}/sharesec \
                     ${bindir}/smbcacls \
                     ${bindir}/smbcquotas \
                     ${bindir}/findsmb \
                     ${bindir}/smbta-util \
                     ${bindir}/eventlogadm \
                    "

# Advanced utilities, generally not needed in our image
FILES:${PN}-utils = "${bindir}/cifsdd \
                     ${bindir}/smbtree \
                     ${bindir}/smbget \
                     ${bindir}/smbspool \
                     ${bindir}/smbtar \
                     ${bindir}/mvxattr \
                     ${bindir}/nmblookup \
                    "

FILES:${PN}-ad-dc = "${sbindir}/samba \
                     ${systemd_system_unitdir}/samba.service \
                     ${libdir}/krb5/plugins/kdb/samba.so \
"
RDEPENDS:${PN}-ad-dc += "samba krb5-kdc"

FILES:registry-tools = "${bindir}/regdiff \
                        ${bindir}/regpatch \
                        ${bindir}/regshell \
                        ${bindir}/regtree"

FILES:${PN}-ldb-tools = "${bindir}/ldbadd \
                         ${bindir}/ldbdel \
                         ${bindir}/ldbedit \
                         ${bindir}/ldbmodify \
                         ${bindir}/ldbrename \
                         ${bindir}/ldbsearch \
                         ${bindir}/oLschema2ldif \
                        "

FILES:${PN}-pdb-tools = "${bindir}/pdb*"
FILES:${PN}-tdb-tools = "${bindir}/tdb*"
FILES:${PN}-libtdb = "${libdir}/libtdb.so.*"

FILES:winbind = "${sbindir}/winbindd \
                 ${bindir}/wbinfo \
                 ${bindir}/ntlm_auth \
                 ${libdir}/samba/idmap \
                 ${libdir}/samba/nss_info \
                 ${libdir}/samba/krb5/winbind_krb5_locator.so \
                 ${libdir}/samba/krb5/winbind_krb5_localauth.so \
                 ${sysconfdir}/init.d/winbind \
                 ${systemd_system_unitdir}/winbind.service \
                "

FILES:libwinbind = "${libdir}/idmap/*.so \
                    ${libdir}/pdb \
                    ${libdir}/gpext \
                    ${libdir}/perfcount \
                    ${libdir}/security/pam_winbind.so \
"

FILES:${PN}-ctdb-tests = "${bindir}/ctdb_run_tests \
                          ${bindir}/ctdb_run_cluster_tests \
                          ${sysconfdir}/ctdb/nodes \
                          ${datadir}/ctdb-tests \
                          ${datadir}/ctdb/tests \
                          ${localstatedir}/lib/ctdb \
                         "

FILES:${PN}-dsdb-modules = "${libdir}/samba/ldb"

FILES:${PN}-testsuite = "${bindir}/gentest \
                         ${bindir}/locktest \
                         ${bindir}/masktest \
                         ${bindir}/ndrdump \
                         ${bindir}/smbtorture"

FILES:${PN}-python = "${PYTHON_SITEPACKAGES_DIR}"

FILES:${PN}-advanced   = "${bindir}/net ${bindir}/profiles ${bindir}/rpcclient ${bindir}/smbcacls ${bindir}/smbcquotas ${bindir}/smbget ${bindir}/smbtar ${libdir}/pdb ${libdir}/rpc/*"

FILES:swat             = "${sbindir}/swat ${datadir}/swat ${libdir}/samba/*.msg"

RDEPENDS:${PN}-pidl:append = " perl"
FILES:${PN}-pidl = "${bindir}/pidl ${datadir}/perl5/Parse"

pkg_postinst:${BPN}-common:append() {
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

pkg_prerm:${BPN}-common:prepend() {
#!/bin/sh
grep -v 'pam_smbpass.so' $D/etc/pam.d/common-password > $D/tmp/common-password
mv $D/tmp/common-password $D/etc/pam.d/common-password
}

pkg_postrm:${BPN}-common:prepend() {
#!/bin/sh
rm $D/etc/samba/distro/smb-vmc.conf 2>/dev/null || true
}
