SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"

PV = "7.1"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = "\
    autofs \
    ca-certificates \
    oe-alliance-base \
    opendroid-enigma2 \
    opdmultiboot \
    opendroid-bootlogo \
    opdboot-files \
    opendroid-spinner \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "ntfs-3g ", d)} \
    hddtemp \
    dosfstools \
    ${PYTHON_PN}-pillow \
    ${PYTHON_PN}-service-identity \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-future \
    ${PYTHON_PN}-pexpect \
    ${PYTHON_PN}-six \
    ${PYTHON_PN}-beautifulsoup4 \
    rtmpdump \
    unrar \
    zip \
    tar \
    curl \
    packagegroup-base-smbfs-client \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "", "ofgwrite", d)} \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    wireless-tools \
    "
