SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r16"

inherit packagegroup

RDEPENDS_${PN} = "\
    oe-alliance-base \
    opennfr-enigma2 \
    opennfr-bootlogo \
    opennfr-version-info \
    opennfr-base-files \
    opennfr-settings \   
    opennfr-missing \ 
    openssh-sftp-server \
    ntfs-3g \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    packagegroup-base-smbfs-utils \
    hddtemp \
    busybox-cron \
    python-imaging \
    python-requests \
    python-cfscrape \
    python-js2py \
    ofgwrite \
    python-gdata \
    python-service-identity \
    libshowiframe \
    dvbsnoop \
    bash \
    enigma2-plugin-drivers-usbserial \ 
    "

