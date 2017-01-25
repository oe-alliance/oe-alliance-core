SUMMARY = "OpenBH Base"
MAINTAINER = "OpenBH"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r3"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

DEPENDS = "openbh-version-info"

RDEPENDS_${PN} = "\
    blackhole-base \
    blackholesocker \
    busybox-cron \
    dvbsnoop \
    hddtemp \
    inadyn-mt \
    libcrypto-compat-0.9.7 \
    mc \
    ntfs-3g \
    oe-alliance-base \
    openbh-bootlogo \
    openbh-enigma2 \
    openbh-spinner \
    openbh-version-info \
    openssh-sftp-server \
    openvpn \
    python-imaging \
    rtmpdump \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    "
