SUMMARY = "OpenNFR Settings files"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/carlo0815/openNFR-settings.git;protocol=https"

FILES_${PN} = "/etc/* /etc/init.d/*"

INHIBIT_PACKAGE_STRIP = "1"
 
ALLOW_EMPTY_${PN} = "1"

PR = "r1"

S="${WORKDIR}/git"

do_install() {
    mkdir -p ${D}/etc/enigma2
    cp -rp ${S}/etc/enigma2/* ${D}/etc/enigma2
    install -d ${D}/etc/init.d
    for f in swap
    do
        install -m 755 ${f} ${D}/etc/init.d/${f}
    done
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
