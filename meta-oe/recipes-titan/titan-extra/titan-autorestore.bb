SUMMARY = "Autorecover settings and installed packages at first boot from backup"
PACKAGES = "${PN}"
MAINTAINER = "Schimmelreiter"
inherit allarch gitpkgv

require conf/license/license-gplv2.inc

FILES:${PN} = "/etc"

SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"

SRC_URI = "git://github.com/openaaf/autorestore.git;protocol=https;branch=master"
S = "${WORKDIR}/git/src"

RDEPENDS:${PN} += "bash"

do_install() {
	install -d ${D}/etc/init.d
	install -d ${D}/etc/rcS.d
	install -m 755 ${S}/fastrestore_openaaf.sh ${D}/etc/init.d/settings-restore
	ln -sf ../init.d/settings-restore ${D}/etc/rcS.d/S75settings-restore
}
