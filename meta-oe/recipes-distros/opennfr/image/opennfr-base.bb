SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r21"

inherit packagegroup

RDEPENDS_${PN} = "\
    ca-certificates \
    oe-alliance-base \
    opennfr-enigma2 \
    opennfr-bootlogo \
    opennfr-version-info \
    opennfr-base-files \
    opennfr-settings \
    openssh-sftp-server \
    nfr4xmultiboot \
    ntfs-3g \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    hddtemp \
    exteplayer3 \
    gstplayer \
    ffmpeg \
    enigma2-plugin-systemplugins-serviceapp \ 	
    python-imaging \
    python-requests \
    python-cfscrape \
    python-js2py \
    ofgwrite \
    python-gdata \
    python-service-identity \
    libshowiframe \
    libdw \
    libelf \
    dvbsnoop \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    enigma2-plugin-drivers-usbserial \ 
    "
