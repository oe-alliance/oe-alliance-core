SUMMARY = "Runtime files for the GTX slot initramfs and graphical recovery"
LICENSE = "MIT"
COMPATIBLE_MACHINE = "^gtxcombo$"
inherit image python3native

# The distro uses one rootfs directory per machine. This recipe runs alongside Enigma2.
IMAGE_ROOTFS = "${WORKDIR}/rootfs"
IMAGE_NAME = "gtx-ramfs-${MACHINE}"
IMAGE_LINK_NAME = ""
IMAGE_FEATURES = ""
IMAGE_LINGUAS = ""
IMAGE_FSTYPES = "gtxramfs"
PACKAGE_INSTALL = "busybox util-linux-blkid kmod dosfstools ca-certificates python3-modules gtx-rc-keymap gtx-hdmimode gtx-recovery"
PACKAGE_INSTALL += "linux-firmware-rtl-nic"

do_image_gtxramfs[depends] += "gtx-recovery:do_deploy python3-native:do_populate_sysroot"
IMAGE_CMD:gtxramfs() {
    install -d ${WORKDIR}/ramdisks
    python3 ${DEPLOY_DIR_IMAGE}/gtx-recovery/ramdisk.py "${IMAGE_ROOTFS}" "${WORKDIR}/ramdisks"
    tar -cf "${IMGDEPLOYDIR}/${IMAGE_NAME}.gtxramfs" -C ${WORKDIR}/ramdisks orm.cpio.gz slot.cpio.gz
}
