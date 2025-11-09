SUMMARY = "Autorecover settings and installed packages at first boot from backup"
MAINTAINER = "OpenATV Team"
inherit autotools pkgconfig gitpkgv

require conf/license/license-gplv2.inc

FILES:${PN} = "/etc /usr/bin"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"
PR = "r1"

SRC_URI = "git://github.com/openatv/autorestore.git;protocol=https;branch=master"

RDEPENDS:${PN} += "bash"

do_install() {
	install -d ${D}/etc/init.d
	install -d ${D}/etc/rcS.d
	install -m 755 ${S}/fastrestore_openatv_v2.sh ${D}/etc/init.d/settings-restore
	ln -sf ../init.d/settings-restore ${D}/etc/rcS.d/S75settings-restore
	install -d ${D}/usr/bin
	install -m 755 ${B}/src/fbprogress ${D}/usr/bin/fbprogress
}
