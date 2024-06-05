DESCRIPTION = "Autorecover settings and install packages at first boot from /media/*/backup"
PACKAGES = "${PN}"
MAINTAINER = "OpenPLi team"

require conf/license/license-gplv2.inc

PV = "2021081701"
SRC_URI = "file://shell-scripts/"

# Need to tell bitbake that we have extra files installed
FILES_${PN} = " \
    ${sysconfdir} \
    ${base_bindir} \
"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

# Not inheriting from rc-update because the script commits suicide, which
# confuses the pkg scripts.
do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    install -d ${D}${base_bindir}
    # run-once initialization script
    install -m 644 ${S}/shell-scripts/convert-smbconf.py ${D}${base_bindir}/convert-smbconf.py
    install -m 755 ${S}/shell-scripts/settings-restore.sh ${D}${sysconfdir}/init.d/settings-restore.sh
    install -m 644 ${S}/shell-scripts/convert-smbconf.py ${D}${base_bindir}/convert-smbconf.py
}

inherit allarch
