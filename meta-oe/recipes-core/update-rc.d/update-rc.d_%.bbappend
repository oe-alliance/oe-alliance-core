require conf/license/license-gplv2.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
           file://update-rc.d.pl \
          "

inherit allarch

DEPENDS += "insserv perl"
DEPENDS_class-native += "insserv perl"
RDEPENDS_${PN} += "insserv perl perl-module-file-glob"

DEPENDS_append_class-target = " insserv perl"
PACKAGE_WRITE_DEPS_append = " ${@bb.utils.contains('DISTRO_FEATURES','systemd','systemd-systemctl-native','',d)}"
PACKAGE_WRITE_DEPS_append = " insserv perl"

do_patch() {
}

do_compile() {
}

do_install_append_class-native() {
	install -m 0755 ${WORKDIR}/update-rc.d.pl ${D}${sbindir}/update-rc.d
}

do_install_append_class-target() {
	install -m 0755 ${WORKDIR}/update-rc.d.pl ${D}${sbindir}/update-rc.d
}

BBCLASSEXTEND = "native"
