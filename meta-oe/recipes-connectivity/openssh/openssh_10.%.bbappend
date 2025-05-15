do_install:append () {
	install -d ${D}${libdir}/openssh
	ln -s ${libexecdir}/sftp-server ${D}${libdir}/openssh/sftp-server
}

FILES:${PN}-sftp-server += " ${libdir}/openssh/sftp-server"
