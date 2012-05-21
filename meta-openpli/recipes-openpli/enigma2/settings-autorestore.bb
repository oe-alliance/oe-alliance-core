PV = "20111105"
PR = "r1"
SRC_URI = "file://*"
DESCRIPTION = "Autorecover settings and install packages at first boot from /media/*/backup"
PACKAGES = "${PN}"
MAINTAINER = "MiLo"

require conf/license/openpli-gplv2.inc

# Need to tell bitbake that we have extra files installed
FILES_${PN} = "/etc"

S = "${WORKDIR}"

# Not inheriting from rc-update because the script commits suicide, which
# confuses the pkg scripts.
do_install() {
	install -d ${D}/etc/init.d
	install -d ${D}/etc/rcS.d
	# run-once initialization script
	install -m 755 ${S}/settings-restore.sh ${D}/etc/init.d/settings-restore.sh
	install -m 755 ${S}/settings-restore.old.sh ${D}/etc/init.d/settings-restore.old.sh
	install -m 755 ${S}/autoinstall.sh ${D}/etc/init.d/autoinstall.sh
}

# Safeguard: Don't activate on a running image
pkg_postinst() {
	if [ "x$D" != "x" ]
	then
		ln -sf ../init.d/settings-restore.sh $D/etc/rcS.d/S20settingsrestore
		ln -sf ../init.d/autoinstall.sh $D/etc/rcS.d/S99autoinstall
	fi
}

inherit allarch
