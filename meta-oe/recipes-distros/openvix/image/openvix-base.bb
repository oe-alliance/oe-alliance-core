SUMMARY = "OpenViX Base"
MAINTAINER = "OpenViX"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r5"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

DEPENDS = "openvix-version-info"

RDEPENDS:${PN} = "\
    autofs \
    ca-certificates \
    ntpd-sync \
    oe-alliance-base \
    openvix-version-info \
    openvix-enigma2 \
    openvix-bootlogo \
    openvix-spinner \
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-imaging", "${PYTHON_PN}-pillow", d)} \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    ${PYTHON_PN}-service-identity \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-future \
    ${PYTHON_PN}-pexpect \
    ${PYTHON_PN}-six \
    rtmpdump \
    wireless-tools \
    zip \
    "
