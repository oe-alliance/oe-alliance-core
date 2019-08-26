SUMMARY = "Autorecover settings and installed packages at first boot from backup"
PACKAGES = "${PN}"
MAINTAINER = "Schimmelreiter"
inherit allarch gitpkgv

require conf/license/license-gplv2.inc

FILES_${PN} = "${sysconfdir}"

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"

SRC_URI = "git://github.com/openatv/autorestore.git;protocol=git"
S = "${WORKDIR}/git/src"

RDEPENDS_${PN} += "bash"

do_install() {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${sysconfdir}/rcS.d
	install -m 755 ${S}/fastrestore_openatv.sh ${D}${sysconfdir}/init.d/settings-restore
	ln -sf ../init.d/settings-restore ${D}${sysconfdir}/rcS.d/S75settings-restore
}
