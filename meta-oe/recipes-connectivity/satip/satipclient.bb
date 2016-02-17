SUMMARY = "satip client using vtuner"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2c1c00f9d3ed9e24fa69b932b7e7aff2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI = " \
    git://github.com/oe-alliance/satip-client.git;protocol=git;branch=next \
    file://satipclient.sh \
"

S = "${WORKDIR}/git"

inherit gitpkgv autotools update-rc.d

INITSCRIPT_NAME = "satipclient"
INITSCRIPT_PARAMS = "defaults"

do_install_append() {
    install -d ${D}/etc/init.d
    install -m 0755 ${WORKDIR}/satipclient.sh ${D}/etc/init.d/satipclient
}

EXTRA_OECONF = " \
    --with-boxtype=${MACHINE} \
    --with-machinebuild="${MACHINEBUILD}" \
    "
