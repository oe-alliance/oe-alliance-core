SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r4"

inherit packagegroup

RDEPENDS_${PN} = "\
    oe-alliance-base \
    octagon-enigma2 \
    octagon-bootlogo \
    openssh-sftp-server \
    ntfs-3g \
    hddtemp \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs \
    packagegroup-base-nfs \
    busybox-cron \
    python-imaging \
    ofgwrite \
    "
