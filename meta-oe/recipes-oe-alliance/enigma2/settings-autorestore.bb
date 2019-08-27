PV = "20111105"
PR = "r0"
SRC_URI = "file://*"
SUMMARY = "Autorecover settings and install packages at first boot from /media/*/backup"
PACKAGES = "${PN}"
MAINTAINER = "MiLo"
inherit allarch

require conf/license/license-gplv2.inc

# Need to tell bitbake that we have extra files installed
FILES_${PN} = "${sysconfdir}"

S = "${WORKDIR}"

# Not inheriting from rc-update because the script commits suicide, which
# confuses the pkg scripts.
do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    # run-once initialization script
    install -m 755 ${S}/settings-restore.sh ${D}${sysconfdir}/init.d/settings-restore.sh
    install -m 755 ${S}/settings-restore.old.sh ${D}${sysconfdir}/init.d/settings-restore.old.sh
    install -m 755 ${S}/autoinstall.sh ${D}${sysconfdir}/init.d/autoinstall.sh
}

# Safeguard: Don't activate on a running image
pkg_postinst_${PN}() {
    if [ "x$D" != "x" ]
    then
        ln -sf ../init.d/settings-restore.sh $D${sysconfdir}/rcS.d/S31settingsrestore
        ln -sf ../init.d/autoinstall.sh $D${sysconfdir}/rcS.d/S99autoinstall
    fi
}
