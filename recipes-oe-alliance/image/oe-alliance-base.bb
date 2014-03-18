SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r15"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "\
    oe-alliance-enigma2 \
    oe-alliance-branding \
    ${@base_contains("MACHINE_FEATURES", "wol", "vuplus-coldboot vuplus-ethwol", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "iniwol", "ini-coldboot ini-ethwol", "", d)} \
    avahi-daemon \
    dropbear \
    early-configure \
    e2fsprogs-mke2fs \
    e2fsprogs-e2fsck \
    e2fsprogs-tune2fs \
    fakelocale \
    libavahi-client \
    modutils-loadscript \
    ntpdate \
    opkg \
    sdparm \
    packagegroup-base \
    packagegroup-core-boot \
    tzdata \
    util-linux-sfdisk \
    util-linux-blkid \
    volatile-media \
    vsftpd \
    "

