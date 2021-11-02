SUMMARY = "Autorecover settings and installed packages at first boot from backup"
PACKAGES = "${PN}"
MAINTAINER = "Schimmelreiter"
inherit allarch gitpkgv

require conf/license/license-gplv2.inc

FILES_${PN} = "/etc"

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"

SRC_URI = "git://github.com/openatv/autorestore.git;protocol=https"
S = "${WORKDIR}/git/src"

RDEPENDS_${PN} += "bash"

do_install() {
	install -d ${D}/etc/init.d
	install -d ${D}/etc/rcS.d
	install -m 755 ${S}/fastrestore_openatv.sh ${D}/etc/init.d/settings-restore
	ln -sf ../init.d/settings-restore ${D}/etc/rcS.d/S75settings-restore
}
