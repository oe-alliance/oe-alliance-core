SUMMARY = "small addon Base packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
#PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

ALLOW_EMPTY:${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r0"


RRECOMMENDS:${PN} = " \
    tzdata \
    wireless-tools \
    e2fsprogs-e2fsck \
    e2fsprogs-tune2fs \
    minilocale \
    libavahi-client \
    libcrypto-compat-0.9.7 \
    libcrypto-compat-1.0.0 \
    libxcrypt-compat \
    avahi-daemon \
    cronie \
    llmnrd \
    sdparm \
    vsftpd \
    wireless-tools \
    parted \
    mtd-utils \
    mtd-utils-ubifs \
"
