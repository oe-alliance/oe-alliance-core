require conf/license/license-gplv2.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
           file://update-rc.d.pl \
          "

inherit allarch

DEPENDS += "perl insserv"
DEPENDS_class-native += "perl insserv"
RDEPENDS_${PN} += "perl insserv"

DEPENDS_append_class-target = " perl insserv"
PACKAGE_WRITE_DEPS_append = " ${@bb.utils.contains('DISTRO_FEATURES','systemd','systemd-systemctl-native','',d)}"
PACKAGE_WRITE_DEPS_append = " perl insserv"

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
