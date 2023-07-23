SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"

PV = "1.0"
PR = "r36"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = "\
    enigma-info \
    openatv-version-info \
    oe-alliance-picon-feed \
    autofs \
    chrony \
    dosfstools \
    oe-alliance-base \
    openatv-bootlogo \
    openssh-sftp-server \
    hdparm \
    smartmontools \
    ${@bb.utils.contains("MACHINE_FEATURES", "nogui", "", "${NORMAL_GUI}", d)} \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    ${@bb.utils.contains("SMALLBOXWIZARD", "1", "${SMALLBOXWIZARD_IMAGE}", "${NORMAL_IMAGE}", d)} \
"

NORMAL_GUI = "\
    openatv-enigma2 \
    openatv-spinner \
    ${PYTHON_PN}-pillow \
    ${PYTHON_PN}-service-identity \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-future \
    ${PYTHON_PN}-pexpect \
    ${PYTHON_PN}-six \
"

SMALLBOXWIZARD_IMAGE = "\
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "${NORMAL_IMAGE}", d)} \
"

NORMAL_IMAGE = "\
    ntfs-3g \
    unrar \
    iproute2 \
    tar \
    ca-certificates \
    flip \
    hddtemp \
    wireless-tools \
    rtmpdump \
    zip \
    ofgwrite \
"