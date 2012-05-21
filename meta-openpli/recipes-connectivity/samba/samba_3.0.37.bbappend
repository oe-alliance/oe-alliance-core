PRINC = "1"

SRC_URI += " \
	file://01samba-kill \
	file://01samba-start \
"

PACKAGES =+ "smbfs smbfs-doc sambaserver libpopt libtalloc"

FILES_smbfs = "${bindir}/smbmount ${bindir}/smbumount ${bindir}/smbmnt ${base_sbindir}/mount.smbfs ${base_sbindir}/mount.smb"
FILES_smbfs-doc = "${mandir}/man8/smbmount.8 ${mandir}/man8/smbumount.8 ${mandir}/man8/smbmnt.8"
FILES_sambaserver = "${sbindir}/smbd ${sbindir}/nmbd ${libdir}/charset/*.so ${libdir}/*.dat \
	${sysconfdir}/samba/smb.conf ${sysconfdir}/samba/private \
	${sysconfdir}/network/if-up.d/01samba-start ${sysconfdir}/network/if-down.d/01samba-kill"
FILES_libpopt = "${libdir}/libpopt.so.*"
FILES_libtalloc = "${libdir}/libtalloc.so.*"

CONFFILES_${PN} = ""
CONFFILES_sambaserver = "${sysconfdir}/samba/smb.conf"

do_install_prepend() {
	install -c -m 644 ${WORKDIR}/smb.conf ../examples/smb.conf.default
}

do_install_append() {
	install -d ${D}${sysconfdir}/samba/private
	install -d ${D}${sysconfdir}/network/if-down.d
	install -m 0755 ${WORKDIR}/01samba-kill ${D}${sysconfdir}/network/if-down.d
	install -d ${D}${sysconfdir}/network/if-up.d
	install -m 0755 ${WORKDIR}/01samba-start ${D}${sysconfdir}/network/if-up.d
}
