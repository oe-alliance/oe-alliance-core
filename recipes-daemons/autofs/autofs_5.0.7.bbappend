PRINC = "2"

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
	echo "/media/net /etc/auto.net --ghost" > ${D}/etc/auto.master
	echo "# automounter configuration" > ${D}/etc/auto.net
	chmod 0644 ${D}/etc/auto.net
	rm -f ${D}/etc/auto.smb ${D}/etc/auto.misc ${D}/etc/autofs_ldap_auth.conf
	sed -i 's/^TIMEOUT=300/TIMEOUT=30/' ${D}/etc/default/autofs
}
