SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "7.0"
PR = "r4"

inherit packagegroup

RDEPENDS_${PN} = " \
    ca-certificates \
    oe-alliance-base \
    openspa-enigma2 \
    openspa-bootlogo \
    openspa-spinner \
    openssh-sftp-server \
    ntfs-3g \
    hddtemp \
    busybox-cron \
    python-beautifulsoup \
    python-html \
    python-imaging \
    python-importlib \
    python-service-identity \
    streamproxy \
    rtmpdump \
    packagegroup-base-smbfs-client \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "", "ofgwrite", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-extensions-openwebif-vxg", d)} \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    bash \
    unrar \
    wget \
    "
