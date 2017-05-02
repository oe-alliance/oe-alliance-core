SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r1"

inherit packagegroup

RDEPENDS_${PN} = "\
    oe-alliance-base \
    openeight-enigma2 \
    openeight-bootlogo \
    hddtemp \
    dosfstools \
    ntfs-3g \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    packagegroup-base-smbfs-utils \
    packagegroup-base-nfs \
    busybox-cron \
    unrar \
    ofgwrite \
    enigma2-plugin-drivers-dvb-usb-af9035 \
    ca-certificates \
    "
