SUMMARY = "Base packages require for image."
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINEBUILD}"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"


inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r0"

RDEPENDS_${PN} = "\
    oe-alliance-enigma2 \
    oe-alliance-branding \
    ${@bb.utils.contains("MACHINE_FEATURES", "wol", "vuplus-coldboot vuplus-ethwol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "wowl", "vuplus-wowl", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "iniwol", "ini-coldboot ini-ethwol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gbwol", "gigablue-ethwol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gbsoftwol", "gigablue-ethsoftwol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "no-nmap", "" , "nmap", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "emmc", "dosfstools mtools e2fsprogs-resize2fs partitions-by-name" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "fastboot", "dosfstools mtools android-tools" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "vubluetooth", "bluetoothsetup-${MACHINE} enigma2-plugin-extensions-witaispeechtotext", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gbbluetooth", "bluetoothsetup-${MACHINE}", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "recovery", "recovery" , "", d)} \
    ${@bb.utils.contains("DEFAULTTUNE", "sh4", "alsa-utils-amixer-conf" , "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "${GETEXTRA}", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "aarch64", "${GETEXTRA}", "", d)} \
    avahi-daemon \
    bash \
    cronie \
    dropbear \
    early-configure \
    e2fsprogs-mke2fs \
    e2fsprogs-e2fsck \
    e2fsprogs-tune2fs \
    minilocale \
    libavahi-client \
    libcrypto-compat-0.9.7 \
    libcrypto-compat-1.0.0 \
    libxcrypt-compat \
    llmnrd \
    modutils-loadscript \
    ntpdate \
    opkg \
    rc-local \
    sdparm \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "", "p7zip", d)} \
    packagegroup-base \
    packagegroup-core-boot \
    tzdata \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "tzdata-europe", "tzdata-europe tzdata-australia tzdata-asia tzdata-pacific tzdata-africa tzdata-americas", d)} \
    util-linux-sfdisk \
    util-linux-blkid \
    util-linux-flock \
    volatile-media \
    vsftpd \
    wget \
    python-twisted-protocols python-numbers python-argparse \
    "

# The following RRECOMMENDS ensure that images on boxes with very limited
# kernel space behave identical to those that have these options built-in
# by including the corresponding kernel modules.
# So far these are xfs and vfat and their dependencies
RRECOMMENDS_${PN} = "\
    kernel-module-xfs \
    kernel-module-exportfs \
    kernel-module-fat \
    kernel-module-msdos \
    kernel-module-vfat \
    kernel-module-nls-cp437 \
    kernel-module-nls-iso8859-1 \
    kernel-module-nls-iso8859-15 \
    "

GETEXTRA = "${@bb.utils.contains('MACHINE', 'cube', '', 'edid-decode', d)}"
