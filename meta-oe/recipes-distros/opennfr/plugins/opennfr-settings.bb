SUMMARY = "OpenNFR Settings files"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/carlo0815/openNFR-settings.git"

FILES_${PN} = "${sysconfdir}/* ${sysconfdir}/init.d/*"

INHIBIT_PACKAGE_STRIP = "1"
 
ALLOW_EMPTY_${PN} = "1"

PR = "r1"

S="${WORKDIR}/git"

do_install() {
    mkdir -p ${D}${sysconfdir}/enigma2
    cp -rp ${S}${sysconfdir}/enigma2/* ${D}${sysconfdir}/enigma2
    install -d ${D}${sysconfdir}/init.d
    for f in swap
    do
        install -m 755 ${f} ${D}${sysconfdir}/init.d/${f}
    done
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
