SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r38"

inherit packagegroup

RDEPENDS_${PN} = "\
    oe-alliance-base \
    ca-certificates \
    openhdf-enigma2 \
    openhdf-bootlogo \
    openhdf-spinner \
    curl \
    hddtemp \
    python-gdata \
    python-requests \
    python-mutagen \
    python-plistlib \
    python-imaging \
    python-service-identity \
    unrar \
    ofgwrite \
    rtmpdump \
    zip \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", \
    " \
    ntfs-3g \
    enigma2-plugin-extensions-openwebif-themes \
    enigma2-plugin-extensions-openwebif-webtv \
    enigma2-plugin-extensions-openwebif-vxg \
    enigma2-plugin-extensions-openwebif-terminal \
    exteplayer3 \
    gstplayer \
    ffmpeg \
    enigma2-plugin-systemplugins-serviceapp \
    zip \
    ", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreambox", "", "ofgwrite", d)} \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "singlecore", "", \
    " \
    packagegroup-base-smbfs-server \
    packagegroup-base-nfs \
    ", d)} \
    packagegroup-base-smbfs-client \
    "
