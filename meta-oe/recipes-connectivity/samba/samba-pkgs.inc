inherit systemd update-rc.d update-alternatives

PACKAGES =+ "smbclient ${PN}-common \
             ${PN}-admin ${PN}-utils \
             ${PN}-ldb-tools ${PN}-tdb-tools ${PN}-pdb-tools registry-tools \
             winbind ${PN}-ad-dc ${PN}-ctdb-tests \
             ${PN}-python3 ${PN}-pidl ${PN}-dsdb-modules ${PN}-testsuite \
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
        d.appendVar('RRECOMMENDS:%s' % pn, ' %s' % pkg)

    mlprefix = d.getVar('MLPREFIX') or ''
    pam_libdir = d.expand('${base_libdir}/security')
    pam_pkgname = mlprefix + 'pam-plugin%s'
    do_split_packages(d, pam_libdir, '^pam_(.*)\.so$', pam_pkgname, 'PAM plugin for %s', extra_depends='', prepend=True)

    libdir = d.getVar('libdir')
    do_split_packages(d, libdir, '^lib(.*)\.so\..*$', 'lib%s', 'Samba %s library', extra_depends='${PN}-common', prepend=True, allow_links=True)
    pkglibdir = '%s' % libdir
    do_split_packages(d, pkglibdir, '^lib(.*)\.so$', 'lib%s', 'Samba %s library', extra_depends='${PN}-common', prepend=True)
    moduledir = '%s/auth' % libdir
    do_split_packages(d, moduledir, '^(.*)\.so$', 'samba-auth-%s', 'Samba %s authentication backend', hook=module_hook, extra_depends='', prepend=True)
    moduledir = '%s/pdb' % libdir
    do_split_packages(d, moduledir, '^(.*)\.so$', 'samba-pdb-%s', 'Samba %s password backend', hook=module_hook, extra_depends='', prepend=True)
}

PACKAGESPLITFUNCS:prepend = "samba_populate_packages "
PACKAGES_DYNAMIC = "pam-pluginsmbpass samba-auth-.* samba-pdb-.*"

# Common to client and server installation
FILES:${BPN}-common = "${sysconfdir}/default \
                       ${sysconfdir}/pam.d/samba \
                       ${sysconfdir}/samba \
                       ${sysconfdir}/tmpfiles.d \
                       ${localstatedir}/lib/samba \
                       ${localstatedir}/spool/samba \
                      "

CONFFILES:${BPN}-common = "${sysconfdir}/pam.d/samba \
                           ${sysconfdir}/samba/smb-user.conf \
                           ${sysconfdir}/samba/private/users.map \
                           ${sysconfdir}/samba/private/smbpasswd \
                           if ${@bb.utils.contains('DISTRO_NAME','openatv','true','false',d)}; then
                               ${sysconfdir}/samba/smb-local.conf
                           fi
                          "

RRECOMMENDS:${BPN}-common += "pam-pluginsmbpass"
INSANE_SKIP:${BPN}-common += "dev-so"

# The Samba client
FILES:smbclient = "${bindir}/smbclient"
RDEPENDS:smbclient += "${BPN}-common"

# The Samba server
FILES:${PN} = " \
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
               ${libdir}/vfs/widelinks.so \
               ${bindir}/smbpasswd \
               ${bindir}/testparm \
               ${libexecdir}/samba/samba-bgqd \
               \
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
                     ${bindir}/dumpmscat \
                     ${bindir}/dbwrap_tool \
                     ${bindir}/tdbbackup \
                     ${bindir}/tdbdump \
                     ${bindir}/tdbrestore \
                     ${bindir}/mdfind \
                     ${bindir}/mdsearch \
                     ${bindir}/tdbtool \
                     ${sbindir}/eventlogadm \
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
                         ${libdir}/ldb/*.so \
                        "

FILES:${PN}-pdb-tools = "${bindir}/pdbedit"

FILES:winbind = "${sbindir}/winbindd \
                 ${bindir}/wbinfo \
                 ${bindir}/ntlm_auth \
                 ${libdir}/samba/idmap \
                 ${libdir}/samba/nss_info \
                 ${libdir}/krb5/winbind_krb5_locator.so \
                 ${libdir}/krb5/async_dns_krb5_locator.so \
                 ${sysconfdir}/init.d/winbind \
                 ${systemd_system_unitdir}/winbind.service \
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

FILES:${PN}-python3 = "${PYTHON_SITEPACKAGES_DIR}"

RDEPENDS:${PN}-pidl:append = " perl"
FILES:${PN}-pidl = "${bindir}/pidl ${datadir}/perl5/Parse"

pkg_postinst:${BPN}-common:append() {
#!/bin/sh

set +e
grep -v 'pam_smbpass.so' $D/etc/pam.d/common-password > $D/tmp/common-password
if [ -e $D/tmp/common-password ]; then
    mv $D/tmp/common-password $D/etc/pam.d/common-password
fi
echo -e "password\toptional\t\t\tpam_smbpass.so use_authtok use_first_pass" >> $D/etc/pam.d/common-password

grep -qE '^kids:' $D/etc/passwd
if [[ $? -ne 0 ]] ; then
    echo 'kids:x:500:500:Linux User,,,:/media:/bin/false' >> $D/etc/passwd
    echo 'kids:!:16560:0:99999:7:::' >> $D/etc/shadow
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
