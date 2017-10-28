SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "7.2"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_${PN} = " \
    ca-certificates \
    oe-alliance-base \
    openspa-enigma2 \
    openspa-bootlogo \
    openssh-sftp-server \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "iproute2", d)} \
    ntfs-3g \
    hddtemp \
    virtual/cron \
    python-imaging \
    python-service-identity \
    streamproxy \
    rtmpdump \
    packagegroup-base-smbfs-client \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-extensions-openwebif-vxg", d)} \
    ${@bb.utils.contains("MACHINE_NAME", "PLAY", "packagegroup-base-smbfs-client packagegroup-base-smbfs-server packagegroup-base-smbfs-utils packagegroup-base-nfs", "" , d)} \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "bash", d)} \
    ofgwrite \
    "
