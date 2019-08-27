require samba36.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/samba-${PV}:"


FILES_${PN}       = "${base_libdir}/security/pam_smbpass.so"

PACKAGECONFIG ??= ""

do_install_append() {
    mkdir -p ${D}${base_libdir}
    mv ${D}${libdir}/security ${D}${base_libdir}

    rm -rf "${D}${localstatedir}/lock"
    rm -rf "${D}${localstatedir}/run"
    rm -rf "${D}/usr"
    rm -rf "${D}${sysconfdir}"
    rm -rf "${D}${localstatedir}"
    rm -rf "${D}${base_sbindir}"
}

pkg_prerm_${PN}_prepend() {
#!/bin/sh
grep -v 'pam_smbpass.so' $D/etc/pam.d/common-password > $D/tmp/common-password
mv $D/tmp/common-password $D/etc/pam.d/common-password
}

pkg_postinst_${PN}_append() {
#!/bin/sh
if [ -n "$D" ]; then
set +e
grep -v 'pam_smbpass.so' $D/etc/pam.d/common-password > $D/tmp/common-password
mv $D/tmp/common-password $D/etc/pam.d/common-password
echo -e "password\toptional\t\t\tpam_smbpass.so nullok use_authtok use_first_pass" >> $D/etc/pam.d/common-password
fi
}
