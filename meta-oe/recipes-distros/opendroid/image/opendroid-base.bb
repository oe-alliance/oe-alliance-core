SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "6.0"
PR = "r17"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_${PN} = "\
    oe-alliance-base \
    opendroid-enigma2 \
    opendroid-bootlogo \
    opendroid-spinner \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "ntfs-3g ", d)} \
    hddtemp \
    busybox-cron \
    python-imaging \
    rtmpdump \
    packagegroup-base-smbfs-client \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreambox", "", "ofgwrite", d)} \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    "
