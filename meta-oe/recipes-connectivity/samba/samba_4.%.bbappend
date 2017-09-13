FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

# Remove acl, cups etc. support.
PACKAGECONFIG_remove = "acl cups"

PR="r0"

SAMBA4_AUTH_MODULES="auth_wbc,auth_server,auth_netlogond,auth_script,auth_samba4"
SAMBA4_IDMAP_MODULES="idmap_ad,idmap_rid,idmap_adex,idmap_hash,idmap_tdb2"
SAMBA4_PDB_MODULES="${@bb.utils.contains('PACKAGECONFIG', 'ldap', 'pdb_ldap,', '', d)}pdb_ads,pdb_wbc_sam,pdb_samba4"
SAMBA4_VFS_MODULES=""
SAMBA4_MODULES="${SAMBA4_AUTH_MODULES},${SAMBA4_IDMAP_MODULES},${SAMBA4_PDB_MODULES}"

SAMBA4_AUTH_MODULES_STATIC="auth_sam,auth_unix"
SAMBA4_IDMAP_MODULES_STATIC=""
SAMBA4_PDB_MODULES_STATIC="pdb_smbpasswd,pdb_tdbsam"
SAMBA4_VFS_MODULES_STATIC="vfs_default,vfs_aio_pthread"
SAMBA4_MODULES_STATIC="${SAMBA4_AUTH_MODULES_STATIC},${SAMBA4_PDB_MODULES_STATIC},${SAMBA4_VFS_MODULES_STATIC}"

EXTRA_OECONF += " \
                 --without-cluster-support \
                 --without-profiling-data \
                 --with-sockets-dir=${localstatedir}/run \
                 --with-logfilebase=${localstatedir}/log \
                 --nopyc \
                 --with-static-modules=${SAMBA4_MODULES_STATIC} \
                "
EXTRA_OECONF_remove = "--with-cluster-support"
EXTRA_OECONF_remove = "--with-profiling-data"
EXTRA_OECONF_remove = "--with-sockets-dir=${localstatedir}/run/samba"

# Remove unused, add own config, init script
SRC_URI += " \
           file://smb.conf \
           file://init.samba \
           file://pam.samba \
           file://users.map \
           file://smbpasswd \
           "

do_install_append() {
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

RDEPENDS_${PN}-base_append = "${BPN}-common"

FILES_${PN}-base      += "${bindir}/smbpasswd ${bindir}/testparm \
                          ${bindir}/smbcontrol ${bindir}/smbstatus \
                          ${sysconfdir}/init.d/samba"
FILES_${BPN}-common   += "${sysconfdir}/pam.d/samba"

CONFFILES_${BPN}-common = "${sysconfdir}/pam.d/samba ${sysconfdir}/samba/smb.conf ${sysconfdir}/samba/private/users.map ${sysconfdir}/samba/private/smbpasswd"

RRECOMMENDS_${PN}-base+= "pam-smbpass wsdd"


pkg_postinst_${BPN}-common_prepend() {
#!/bin/sh

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

