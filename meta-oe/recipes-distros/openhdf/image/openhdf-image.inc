SUMMARY = "OpenHDF Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openHDF Team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATE}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR[vardepsexclude] += "DATE"
REPRODUCIBLE_TIMESTAMP_ROOTFS = "${@time.strftime('%s')}"

WORKDIR = "${TMPDIR}/work/${MULTIMACH_TARGET_SYS}/${PN}/${EXTENDPE}${PV}"

do_rootfs[deptask] = "do_rm_work"

IMAGE_INSTALL = "openhdf-base \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvbc-only", "", "enigma2-plugin-settings-defaultsat", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", \
    " \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    packagegroup-base-nfs \
    ", d)} \
    "
# Some additional comfort on the shell: Pre-install nano on boxes with 128 MB or more:
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96", "", "nano", d)}"

# ... plus mc and helpers on 256 MB or more:
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "mc mc-fish mc-helpers", d)}"

export IMAGE_BASENAME = "openhdf-image"
# Prevent locales-archive creation to save some space
IMAGE_LOCALES_ARCHIVE = ""

# 64 or 128MB of flash: No language files, above: German and French
IMAGE_LINGUAS  = "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "de-de fr-fr", d)}"

# Add more languages for 512 or more MB of flash:
IMAGE_LINGUAS += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128 256", "", "es-es it-it nl-nl pt-pt", d)}"

IMAGE_FEATURES += "package-management"

INHIBIT_DEFAULT_DEPS = "1"

inherit image

do_package_index[nostamp] = "1"
do_package_index[depends] += "${PACKAGEINDEXDEPS}"

rootfs_postprocess() {
    if [ -f ~/bin/parser.sh ]; then
        cp ~/bin/parser.sh .
        ./parser.sh ${MACHINEBUILD} ${IMAGE_ROOTFS}
        rm -rf parser.sh
    fi
    cd ${IMAGE_ROOTFS}
    ln -s usr/share/enigma2/spinner usr/share/enigma2/skin_default/spinner || true

    echo ${DEPLOY_DIR_IMAGE} > /tmp/DEPLOY_DIR_IMAGE
}

ROOTFS_POSTPROCESS_COMMAND += "rootfs_postprocess; "

python do_package_index() {
    from oe.rootfs import generate_index_files
    generate_index_files(d)
}
addtask do_package_index after do_rootfs before do_image_complete
