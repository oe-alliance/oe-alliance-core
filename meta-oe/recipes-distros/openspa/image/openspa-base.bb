SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"

PV = "8.3"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = " \
    autofs \
    ca-certificates \
    chrony \
    flip \
    hddtemp \
    wireless-tools \
    oe-alliance-base \
    openspa-bootlogo \
    openspa-enigma2 \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "openssh-sftp-server ", d)} \
    packagegroup-base-smbfs-client \
    ${PYTHON_PN}-pillow \
    ${PYTHON_PN}-service-identity \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-future \
    ${PYTHON_PN}-pexpect \
    ${PYTHON_PN}-six \
    rtmpdump \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    ofgwrite \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "iproute2 tar", d)} \
    ${@bb.utils.contains_any("FLASHSIZE", "64 96", "", "ntfs-3g unrar zip", d)} \
    openvpn-script \
    mhw2-files \
    "
