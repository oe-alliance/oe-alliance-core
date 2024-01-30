SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"

PV = "1.0"
PR = "r48"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = "\
    autofs \
    ca-certificates \
    hddtemp \
    oe-alliance-base \
    ofgwrite \
    openhdf-bootlogo \
    openhdf-enigma2 \
    openhdf-radio-feed \
    openhdf-spinner \
    packagegroup-base-smbfs-client \
    ${PYTHON_PN}-future \
    ${PYTHON_PN}-mutagen \
    ${PYTHON_PN}-netifaces \
    ${PYTHON_PN}-pexpect \
    ${PYTHON_PN}-pillow \
    ${PYTHON_PN}-plistlib \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-service-identity \
    ${PYTHON_PN}-six \
    rtmpdump \
    unrar \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", \
    " \
    enigma2-plugin-extensions-openwebif-terminal \
    enigma2-plugin-extensions-openwebif-themes \
    enigma2-plugin-extensions-openwebif-vxg \
    enigma2-plugin-extensions-openwebif-webtv \
    exteplayer3 \
    ffmpeg \
    gstplayer \
    ntfs-3g \
    packagegroup-base-nfs \
    packagegroup-base-smbfs-server \
    zip \
    ", d)} \
    "
