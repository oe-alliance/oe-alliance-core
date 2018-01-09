SUMMARY = "OpenATV Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openATV team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

do_rootfs[deptask] = "do_rm_work"

IMAGE_INSTALL = " \
    bash \
    ca-certificates \
    flip \
    hddtemp \
    nano \
    mc \
    oe-alliance-base \
    openatv-bootlogo \
    openatv-enigma2 \
    openatv-spinner \
    openssh-sftp-server \
    packagegroup-base-smbfs-client \
    python-imaging \
    python-service-identity \
    rtmpdump \
    ${@bb.utils.contains("TUNE_FEATURES", "armv", "glibc-compat", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "", "ofgwrite", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", " \
        iproute2 \
        ntfs-3g \
    ", d)} \
    \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvbc-only", "", "enigma2-plugin-settings-defaultsat", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "no-cl-svr", "", \
    " \
    packagegroup-base-smbfs-server \
    packagegroup-base-nfs \
    ", d)} \
    "
# Some additional comfort on the shell: Pre-install nano on boxes with 128 MB or more:
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96", "", "nano", d)}"

# ... plus mc and helpers on 256 MB or more:
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "mc mc-fish mc-helpers", d)}"

rootfs_timestamp() {
    date -u '+%Y-%m-%d %H:%M:%S' > ${IMAGE_ROOTFS}/etc/fake-hwclock.data
}

export IMAGE_BASENAME = "openatv-image"
# 64 or 128MB of flash: No language files, above: German and French
IMAGE_LINGUAS  = "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "de-de fr-fr", d)}"

# Add more languages for 512 or more MB of flash:
IMAGE_LINGUAS += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128 256", "", "es-es it-it nl-nl pt-pt", d)}"

IMAGE_FEATURES += "package-management"

inherit image

ROOTFS_POSTPROCESS_COMMAND += "rootfs_timestamp; "
