SUMMARY = "Inittab for sysvinit"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

require conf/license/license-gplv2.inc

SRC_URI = "file://inittab"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

INHIBIT_DEFAULT_DEPS = "1"

do_compile() {
    :
}

do_install() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${UNPACKDIR}/inittab ${D}${sysconfdir}/inittab
}
