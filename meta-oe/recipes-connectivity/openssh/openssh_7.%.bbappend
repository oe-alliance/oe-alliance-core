do_install_append () {
	install -d ${D}${libdir}/openssh
	ln -s ${libexecdir}/sftp-server ${D}${libdir}/openssh/sftp-server
}

FILES_${PN}-sftp-server += " ${libdir}/openssh/sftp-server"
