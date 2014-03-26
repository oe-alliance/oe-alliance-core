SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r5"

inherit packagegroup

RDEPENDS_${PN} = "\
    oe-alliance-base \
    odinsupport-enigma2 \
    odinsupport-bootlogo \
    odinsupport-spinner \
    ntfs-3g \
    hddtemp \
    busybox-cron \
    "
