SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r29"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_${PN} = "\
    ca-certificates \
    flip \
    hddtemp \
    oe-alliance-base \
    openatv-bootlogo \
    openatv-enigma2 \
    openatv-spinner \
    openssh-sftp-server \
    packagegroup-base-smbfs-client \
    python-imaging \
    python-service-identity \
    rtmpdump \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "", "ofgwrite", d)} \
    ${@bb.utils.contains_any("FLASHSIZE", "64", "", " \
        iproute2 \
        ntfs-3g \
    ", d)} \
    "
