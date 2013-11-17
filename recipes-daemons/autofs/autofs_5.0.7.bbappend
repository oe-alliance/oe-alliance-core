PRINC = "5"

EXTRA_OECONF += "--with-confdir=/etc/default"

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
    install -d ${D}/etc
    echo "/autofs     /etc/auto.hotplug" > ${D}/etc/auto.master
    echo "/media/autofs  /etc/auto.network" > ${D}/etc/auto.master
    echo "# automounter configuration" > ${D}/etc/auto.network
    chmod 0644 ${D}/etc/auto.network
    echo "* -fstype=auto,rw,sync :/dev/&" > ${D}/etc/auto.hotplug
    chmod 0644 ${D}/etc/auto.hotplug	
    rm -f ${D}/etc/auto.smb ${D}/etc/auto.misc ${D}/etc/autofs_ldap_auth.conf
    install -d ${D}/etc/default
    sed -i 's/^TIMEOUT=300/TIMEOUT=30/' ${D}/etc/default/autofs
}
