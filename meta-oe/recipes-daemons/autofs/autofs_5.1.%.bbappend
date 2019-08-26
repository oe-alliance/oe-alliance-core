B = "${S}"

INITSCRIPT_PARAMS = "defaults 17"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " file://volatiles.99_autofs file://auto.network file://autofs.default"

EXTRA_OECONF += "--with-confdir=${sysconfdir}/default --with-mapdir=${sysconfdir}"

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
    echo "/media/autofs  ${sysconfdir}/auto.network  --ghost" > ${D}${sysconfdir}/auto.master
    rm -f ${D}${sysconfdir}/auto.smb ${D}${sysconfdir}/auto.misc ${D}${sysconfdir}/autofs_ldap_auth.conf
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        rm ${D}${sysconfdir}/init.d/autofs || true
    else
        [ -e ${D}${sysconfdir}/init.d/autofs ] && sed -i 's/count -lt 15/count -lt 60/' ${D}${sysconfdir}/init.d/autofs
        [ -e ${D}${sysconfdir}/init.d/autofs ] && sed -i 's/sleep 20/sleep 1/' ${D}${sysconfdir}/init.d/autofs
    fi
    install -d ${D}${sysconfdir}/default/volatiles
    install -m 644 ${WORKDIR}/volatiles.99_autofs ${D}${sysconfdir}/default/volatiles/99_autofs
    install -m 644 ${WORKDIR}/auto.network ${D}${sysconfdir}/auto.network
    install -m 644 ${WORKDIR}/autofs.default ${D}${sysconfdir}/default/autofs
}

CONFFILES_${PN} = "${sysconfdir}/auto.network"
