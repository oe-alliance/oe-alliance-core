SUMMARY = "OpenBh Base"
MAINTAINER = "OpenBh"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r11"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

DEPENDS = "openbh-version-info"

RDEPENDS:${PN} = "\
    autofs \
    blackhole-base \
    blackholesocker \
    ca-certificates \
    curl \
    dvbsnoop \
    hddtemp \
    inadyn-mt \
    libcrypto-compat-0.9.7 \
    oe-alliance-base \
    openbh-bootlogo \
    openbh-enigma2 \
    openbh-spinner \
    openbh-version-info \
    openssh-sftp-server \
    openvpn \
    python3-pillow \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    python3-service-identity \
    python3-requests \
    python3-future \
    python3-pexpect \
    python3-six \
    python3-trio \
    rtmpdump \
    zip \
    ${@bb.utils.contains_any("FLASHSIZE", "64 96", "", " \
        ntfs-3g \
        wireless-tools \
    ", d)} \
    "


