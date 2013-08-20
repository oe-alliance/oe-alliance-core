DESCRIPTION = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r2"

inherit packagegroup

RDEPENDS_${PN} = "\
    oe-alliance-base \
    axassupport-enigma2 \
    axassupport-bootlogo \
    axassupport-feed-config \
    libcrypto-compat \
    ntfs-3g \
    hddtemp \
    task-base-smbfs-client \
    task-base-smbfs \
    task-base-nfs \
    busybox-cron \
    "
