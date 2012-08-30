PRINC = "5"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " file://ntpdate.initd"

do_install_append() {
	install -d ${D}/${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/ntpdate.initd ${D}/${sysconfdir}/init.d/ntpdate
}

pkg_postinst_ntpdate_append() {
if test "x$D" != "x"; then
	OPT="-r $D"
else
	OPT="-s"
fi
update-rc.d $OPT ntpdate defaults 41
}

pkg_postrm_ntpdate() {
#!/bin/sh
update-rc.d $D ntpdate remove
}

FILES_ntpdate_append = " ${sysconfdir}/init.d/ntpdate"
