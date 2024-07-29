SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"

PV = "7.4"
PR = "r10"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = "\
    enigma-info \
    autofs \
    ca-certificates \
    ofgwrite \
    openvpn \
    oe-alliance-base \
    opendroid-enigma2 \
    opdmultiboot \
    opendroid-bootlogo \
    opdboot-files \
    opendroid-spinner \
    packagegroup-base-smbfs-client \
    openssh-sftp-server \
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
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", \
    " \
    exteplayer3 \
    ffmpeg \
    gstplayer \
    ntfs-3g \
    packagegroup-base-nfs \
    packagegroup-base-smbfs-server \
    zip \
    tar \
    curl \
    ", d)} \
    "
