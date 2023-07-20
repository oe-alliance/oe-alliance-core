SUMMARY = "Base packages require for image."
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINEBUILD}"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"


inherit packagegroup

ALLOW_EMPTY:${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r3"

RDEPENDS:${PN} = "\
    oe-alliance-feeds-configs \
    oe-alliance-botfeed-configs \
    ${@bb.utils.contains("MACHINE_FEATURES", "nogui", "", "oe-alliance-enigma2", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "wol", "vuplus-coldboot vuplus-ethwol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "wowl", "vuplus-wowl", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "iniwol", "ini-coldboot ini-ethwol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gbwol", "gigablue-ethwol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gbsoftwol", "gigablue-ethsoftwol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "" , "nmap", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "emmc", "dosfstools mtools e2fsprogs-resize2fs partitions-by-name" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "fastboot", "dosfstools mtools android-tools" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "recovery", "recovery" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "vubluetooth", "enigma2-plugin-systemplugins-bluetoothsetup enigma2-plugin-extensions-witaispeechtotext", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gbbluetooth", "enigma2-plugin-systemplugins-bluetoothsetup", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "kexecmb", "kexec-multiboot", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "displayvfd", "displayvfd", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "${GETEXTRA}", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "aarch64", "${GETEXTRA}", "", d)} \
    bash \
    cronie \
    dropbear \
    early-configure \
    e2fsprogs-mke2fs \
    modutils-loadscript \
    opkg \
    parted \
    procps \
    rc-local \
    p7zip \
    packagegroup-base \
    packagegroup-core-boot \
    util-linux-sfdisk \
    util-linux-blkid \
    util-linux-flock \
    volatile-media \
    wireless-tools \
    wget \
    ${@bb.utils.contains("SMALLBOXWIZARD", "1", "${SMALLBOXWIZARD_IMAGE}", "${NORMAL_IMAGE}", d)} \
"

SMALLBOXWIZARD_IMAGE = "\
     ${@bb.utils.contains_any("MACHINE_FEATURES", "smallflash", "", "${NORMAL_IMAGE}", d)} \
"

NORMAL_IMAGE = "\
    tzdata \
    e2fsprogs-e2fsck \
    e2fsprogs-tune2fs \
    minilocale \
    libavahi-client \
    libcrypto-compat-0.9.7 \
    libcrypto-compat-1.0.0 \
    libxcrypt-compat \
    avahi-daemon \
    llmnrd \
    sdparm \
    vsftpd \
    mtd-utils \
    mtd-utils-ubifs \
"

# The following RRECOMMENDS ensure that images on boxes with very limited
# kernel space behave identical to those that have these options built-in
# by including the corresponding kernel modules.
# So far these are xfs and vfat and their dependencies
RRECOMMENDS:${PN} = "\
    kernel-module-xfs \
    kernel-module-exportfs \
    kernel-module-fat \
    kernel-module-msdos \
    kernel-module-vfat \
    kernel-module-nls-cp437 \
    kernel-module-nls-iso8859-1 \
    kernel-module-nls-iso8859-15 \
    "

GETEXTRA = "edid-decode"
