SUMMARY = "satip client for use with vtuner frontend"
HOMEPAGE = "https://code.google.com/p/satip/"
SECTION = "network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${S}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "0.0.3+git${SRCPV}"
PKGV = "0.0.3+git${GITPKGV}"
VER ="0.0.3"
PR = "r2"

CFLAGS_prepend = "-Wall -g -DVTUNER_TYPE_ORIGINAL"

CFLAGS_prepend_vuduo = "-Wall -g -DVTUNER_TYPE_VUPLUS"
CFLAGS_prepend_vuduo2 = "-Wall -g -DVTUNER_TYPE_VUPLUS"
CFLAGS_prepend_vusolo = "-Wall -g -DVTUNER_TYPE_VUPLUS"
CFLAGS_prepend_vusolo2 = "-Wall -g -DVTUNER_TYPE_VUPLUS"
CFLAGS_prepend_vusolose = "-Wall -g -DVTUNER_TYPE_VUPLUS"
CFLAGS_prepend_vuultimo = "-Wall -g -DVTUNER_TYPE_VUPLUS"
CFLAGS_prepend_vuuno = "-Wall -g -DVTUNER_TYPE_VUPLUS"
CFLAGS_prepend_vuzero = "-Wall -g -DVTUNER_TYPE_VUPLUS"
CFLAGS_prepend_vuzero = "-Wall -g -DVTUNER_TYPE_VUPLUS"

CFLAGS_prepend_blackbox7405 = "-Wall -g -DVTUNER_TYPE_BLACKBOX"

SRC_URI = "git://github.com/oe-alliance/satip-client.git"

S = "${WORKDIR}/git"

inherit update-rc.d

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults"

do_install () {
        install -d ${D}${bindir}/
        install -m 0755 ${B}/satip-client ${D}${bindir}/
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${S}/${PN}.sh ${D}${sysconfdir}/init.d/${PN}
        install -d ${D}/etc
        install -m 0644 ${S}/${PN}.conf ${D}${sysconfdir}/${PN}.conf
}

CONFFILES_${PN} = "${sysconfdir}/${PN}.conf"