PRINC = "13"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " file://volatiles.99_autofs file://auto.network file://autofs.default"

EXTRA_OECONF += "--with-confdir=/etc/default --with-mapdir=/etc"

# Remove bash scripting from init script (meaning, remove "function"
# from each shell function)
do_configure_prepend () {
	for bashfile in redhat/autofs.init.in samples/rc.autofs.in
	do
		sed -i 's.#!/bin/bash.#!/bin/sh.' $bashfile
		sed -i 's/^function //g' $bashfile
	done
}

# Remove and change configuration files
do_install_append() {
    echo "/media/autofs  /etc/auto.network  --ghost" > ${D}/etc/auto.master
    rm -f ${D}/etc/auto.smb ${D}/etc/auto.misc ${D}/etc/autofs_ldap_auth.conf
    sed -i 's/count -lt 15/count -lt 60/' ${D}/etc/init.d/autofs
    sed -i 's/sleep 20/sleep 1/' ${D}/etc/init.d/autofs
    install -d ${D}${sysconfdir}/default/volatiles
    install -m 644 ${WORKDIR}/volatiles.99_autofs ${D}${sysconfdir}/default/volatiles/99_autofs
    install -m 644 ${WORKDIR}/auto.network ${D}/etc/auto.network
    install -m 644 ${WORKDIR}/autofs.default ${D}/etc/default/autofs
}

CONFFILES_${PN} = "/etc/auto.network"
