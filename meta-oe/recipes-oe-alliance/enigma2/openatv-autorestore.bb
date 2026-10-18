SUMMARY = "Autorecover settings and installed packages at first boot from backup"
MAINTAINER = "OpenATV Team"
inherit gitpkgv

require conf/license/license-gplv2.inc

FILES:${PN} = "/etc /usr/bin"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"
PR = "r2"

SRC_URI = "gitsm://github.com/openatv/autorestore.git;protocol=https;branch=master"

RDEPENDS:${PN} += "bash"

do_configure[noexec] = "1"

do_compile() {
	oe_runmake fbprogress
}

do_install() {
	install -d ${D}/etc/init.d
	install -d ${D}/etc/rcS.d
	install -m 755 ${S}/fastrestore_openatv_v3.sh ${D}/etc/init.d/settings-restore
	ln -sf ../init.d/settings-restore ${D}/etc/rcS.d/S75settings-restore
	install -d ${D}/usr/bin
	install -m 755 ${S}/fbprogress ${D}/usr/bin/fbprogress
}
