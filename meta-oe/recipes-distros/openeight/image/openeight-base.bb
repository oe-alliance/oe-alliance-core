SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
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
    packagegroup-base-nfs \
    unrar \
    ofgwrite \
    enigma2-plugin-drivers-dvb-usb-af9035 \
    ca-certificates \
    "
