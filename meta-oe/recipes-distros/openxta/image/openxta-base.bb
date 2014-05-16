SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r11"

inherit packagegroup

RDEPENDS_${PN} = "\
    oe-alliance-base \
    openxta-enigma2 \
    openxta-bootlogo \
    hddtemp \
    dosfstools \
    ntfs-3g \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs \
    packagegroup-base-nfs \
    busybox-cron \
    unrar \
    ofgwrite \
    enigma2-plugin-drivers-dvb-usb-af9035 \
    "

