SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "${IMAGE_VERSION}"
PR = "r3"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_${PN} = "\
    ca-certificates \
    ${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", \
    " \
    enigma2-plugin-skins-pli-hd \
    ", d)} \
    oe-alliance-base \
    teamblue-enigma2 \
    teamblue-bootlogo \
    teamblue-spinner \
    teamblue-scripts \
    openssh-sftp-server \
    ntfs-3g \
    ntfsprogs \
    hddtemp \
    python-imaging \
    python-service-identity \
    python-netifaces \
    rtmpdump \
    zip \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    ${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "enigma2-plugin-extensions-openwebif-webtv", \
    " \
    enigma2-plugin-extensions-openwebif-terminal \
    enigma2-plugin-extensions-openwebif-themes \
    enigma2-plugin-extensions-openwebif-vxg \
    ", d)} \
    ${@bb.utils.contains_any("FLASHSIZE", "64", "", \
    " \
    packagegroup-base-smbfs-server \
    packagegroup-base-nfs \
    ", d)} \
    packagegroup-base-smbfs-client \
    ofgwrite \
    "
