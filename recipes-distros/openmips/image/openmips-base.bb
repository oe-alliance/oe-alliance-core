SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r7"

inherit packagegroup

RDEPENDS_${PN} = "\
    oe-alliance-base \
    openmips-enigma2 \
    openmips-bootlogo \
    openmips-spinner \
    libcrypto-compat \
    hddtemp \
    dosfstools \
    ntfs-3g \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs \
    packagegroup-base-nfs \
    busybox-cron \
    python-imaging \
    ofgwrite \
    "
