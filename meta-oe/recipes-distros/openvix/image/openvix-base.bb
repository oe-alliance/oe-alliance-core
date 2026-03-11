SUMMARY = "OpenViX Base"
MAINTAINER = "OpenViX"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r6"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

DEPENDS = "openvix-version-info"

RDEPENDS:${PN} = "\
    autofs \
    ntpd-sync \
    oe-alliance-base \
    openvix-version-info \
    openvix-enigma2 \
    openvix-bootlogo \
    openvix-spinner \
    fstrim-cron \
    python3-pillow \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    python3-service-identity \
    python3-requests \
    python3-future \
    python3-pexpect \
    python3-six \
    python3-trio \
    "

RRECOMMENDS:${PN} = "\
    ${@bb.utils.contains("SMALLBOXWIZARD", "1", "${SMALLBOXWIZARD_IMAGE}", "${NORMAL_IMAGE}", d)} \
    "

SMALLBOXWIZARD_IMAGE = "\
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "${NORMAL_IMAGE}", d)} \
"

NORMAL_IMAGE = "\
    ca-certificates \
    rtmpdump \
    wireless-tools \
    zip \
"
