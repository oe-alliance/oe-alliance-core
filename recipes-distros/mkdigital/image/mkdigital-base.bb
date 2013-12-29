SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r4"

inherit packagegroup

RDEPENDS_${PN} = "\
    oe-alliance-base \
    mkdigital-enigma2 \
    mkdigital-bootlogo \
    libcrypto-compat \
    ntfs-3g \
    hddtemp \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs \
    packagegroup-base-nfs \
    busybox-cron \
    "
