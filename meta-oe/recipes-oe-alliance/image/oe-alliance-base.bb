SUMMARY = "Base packages require for image."
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINEBUILD}"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"


inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r3"

RDEPENDS_${PN} = "\
    oe-alliance-enigma2 \
    oe-alliance-branding \
    ${@bb.utils.contains("MACHINE_FEATURES", "wol", "vuplus-coldboot vuplus-ethwol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "wowl", "vuplus-wowl", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "iniwol", "ini-coldboot ini-ethwol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gbwol", "gigablue-ethwol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gbsoftwol", "gigablue-ethsoftwol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "no-nmap", "" , "nmap", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "emmc", "parted dosfstools mtools" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "vubluetooth", "enigma2-plugin-systemplugins-bluetoothsetup", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "recovery", "recovery" , "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "sh4", "alsa-utils-amixer-conf" , "", d)} \
    avahi-daemon \
    dropbear \
    early-configure \
    e2fsprogs-mke2fs \
    e2fsprogs-e2fsck \
    e2fsprogs-tune2fs \
    fakelocale \
    libavahi-client \
    libcrypto-compat-0.9.7 \
    llmnrd \
    modutils-loadscript \
    ntpdate \
    opkg \
    sdparm \
    packagegroup-base \
    packagegroup-core-boot \
    tzdata tzdata-europe tzdata-australia tzdata-asia tzdata-pacific tzdata-africa tzdata-americas \
    util-linux-sfdisk \
    util-linux-blkid \
    util-linux-flock \
    volatile-media \
    vsftpd \
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
