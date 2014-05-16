SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r4"

inherit packagegroup

RDEPENDS_${PN} = "\
    oe-alliance-base \
    openview-enigma2 \
    openview-bootlogo \
    openview-spinner \
    openssh-sftp-server \
    ntfs-3g \
    hddtemp \
    busybox-cron \
    python-imaging \
    ofgwrite \
    bash \
    "
