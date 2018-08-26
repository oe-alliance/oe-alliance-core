FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://CVE-2018-15473.patch"

do_install_append () {
	install -d ${D}${libdir}/openssh
	ln -s ${libexecdir}/sftp-server ${D}${libdir}/openssh/sftp-server
}

FILES_${PN}-sftp-server += " ${libdir}/openssh/sftp-server"
