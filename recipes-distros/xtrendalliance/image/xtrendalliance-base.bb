DESCRIPTION = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r7"

inherit packagegroup

RDEPENDS_${PN} = "\
    oe-alliance-base \
    xtrendalliance-enigma2 \
    xtrendalliance-bootlogo \
    libcrypto-compat \
    hddtemp \
    dosfstools \
    ntfs-3g \
    packagegroup-smbfs-client \
    packagegroup-smbfs \
    packagegroup-nfs \
    busybox-cron \
    unrar \
    ofgwrite \
    "

