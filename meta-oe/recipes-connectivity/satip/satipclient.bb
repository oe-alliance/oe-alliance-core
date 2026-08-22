SUMMARY = "satip client using vtuner"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2c1c00f9d3ed9e24fa69b932b7e7aff2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = "${AUTOREV}"
PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

SRC_URI = " \
    git://github.com/oe-alliance/satip-client.git;protocol=https;branch=mis \
    file://satipclient.sh \
"

inherit gitpkgv autotools update-rc.d

INITSCRIPT_NAME = "satipclient"
INITSCRIPT_PARAMS = "defaults"

do_install:append() {
    install -d ${D}/etc/init.d
    install -m 0755 ${UNPACKDIR}/satipclient.sh ${D}/etc/init.d/satipclient
}

EXTRA_OECONF = " \
    --with-boxtype=${MACHINE} \
    --with-machinebuild="${MACHINEBUILD}" \
    "
