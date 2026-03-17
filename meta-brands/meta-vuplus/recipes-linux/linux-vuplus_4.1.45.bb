SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPL-2.0-only"
SECTION = "kernel"

MODULE = "linux-4.1.45"

COMPATIBLE_MACHINE = "^(vuduo4k|vuduo4kse)$"

MACHINE_KERNEL_PR = "r8"

inherit kernel machine_kernel_pr kernel-fixups

KSRC_VER = "4.1-1.17"

SRC_URI[md5sum] = "7b5617dc7ec01ff4347b5b50d9d46f0f"
SRC_URI[sha256sum] = "18ecc79522f6b975c96cca7975eb31db93d2e8cfea6755451bba0a1a28feb0bd"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "https://source.mynonpublic.com/vuplus/release/kernel/stblinux-${KSRC_VER}.tar.bz2 \
    file://defconfig \
    file://linux_dvb_adapter.patch \
    file://linux_dvb-core.patch \
    file://linux_4_1_45_dvbs2x.patch \
    file://bcmsysport_4_1_45.patch \
    file://linux_usb_hub.patch \
    file://0001-regmap-add-regmap_write_bits.patch \
    file://0002-af9035-fix-device-order-in-ID-list.patch \
    file://0003-Add-support-for-dvb-usb-stick-Hauppauge-WinTV-soloHD.patch \
    file://0004-af9035-add-USB-ID-07ca-0337-AVerMedia-HD-Volar-A867.patch \
    file://0005-Add-support-for-EVOLVEO-XtraTV-stick.patch \
    file://0006-dib8000-Add-support-for-Mygica-Geniatech-S2870.patch \
    file://0007-dib0700-add-USB-ID-for-another-STK8096-PVR-ref-desig.patch \
    file://0008-add-Hama-Hybrid-DVB-T-Stick-support.patch \
    file://0009-Add-Terratec-H7-Revision-4-to-DVBSky-driver.patch \
    file://0010-media-Added-support-for-the-TerraTec-T1-DVB-T-USB-tu.patch \
    file://0011-media-tda18250-support-for-new-silicon-tuner.patch \
    file://0012-media-dib0700-add-support-for-Xbox-One-Digital-TV-Tu.patch \
    file://0013-mn88472-Fix-possible-leak-in-mn88472_init.patch \
    file://0014-staging-media-Remove-unneeded-parentheses.patch \
    file://0015-staging-media-mn88472-simplify-NULL-tests.patch \
    file://0016-mn88472-fix-typo.patch \
    file://0017-mn88472-finalize-driver.patch \
    ${KERNEL_PATCH_DVB_WINTV_DUALHD} \
    ${KERNEL_PATCH_DVB_USB_A867} \
    ${KERNEL_PATCH_GCC_SERIES} \
    ${KERNEL_PATCH_TBS_USB_41} \
    ${KERNEL_PATCH_TBS_USB_ENUM} \
    ${KERNEL_PATCHES_DVB_STV_SERIES} \
    file://v3-1-2-em28xx-Ignore-errors-while-reading-from-eeprom.patch \
    file://v3-2-2-em28xx-add-support-for-new-of-Terratec-H6.patch \
    ${KERNEL_PATCH_BINUTILS241_V1} \
    ${KERNEL_PATCHES_DVB_SI2168_D60} \
    "

S = "${UNPACKDIR}/linux"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_IMAGETYPE = "zImage"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/zImage"

kernel_do_install:append() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
}

kernel_do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}" EXTRA_CFLAGS="-Wno-attribute-alias"
        if test "${KERNEL_IMAGETYPE_FOR_MAKE}.gz" = "${KERNEL_IMAGETYPE}"; then
                gzip -9c < "${KERNEL_IMAGETYPE_FOR_MAKE}" > "${KERNEL_OUTPUT}"
        fi
}

pkg_postinst:kernel-image () {
        if [ -d /proc/stb ] ; then
            DEST="/dev/${MTD_KERNEL}"
            if [ -f /proc/cmdline ]; then
                args=`cat /proc/cmdline`
                for x in ${args};
                do
                    case "$x" in
                        root=*)
                            ROOT_DEST="${x#root=}"
                        ;;
                        kernel=*)
                            KERNEL_DEST="${x#kernel=}"
                        ;;
                    esac
                done
            fi

            if echo ${ROOT_DEST} | grep -qi "UUID="; then
                DEVICE=$(blkid | sed -n "/${ROOT_DEST#*=}/s/\([^:]\+\):.*/\\1/p")
                if [ x${DEVICE} != x ]; then
                    grep "^${DEVICE}" /proc/mounts | cut -d " " -f 2
                    ROOT_DEST=`grep "^${DEVICE}" /proc/mounts | cut -d " " -f 2`
                fi
            elif echo ${ROOT_DEST} | grep -q "^/dev/mmcblk"; then
                ROOT_DEST=/boot
            else
                ROOT_DEST=`grep "^${ROOT_DEST}" /proc/mounts | cut -d " " -f 2`
            fi

            if [ -f "${ROOT_DEST}/${KERNEL_DEST}" ]; then
                echo "Kernel is located at ${ROOT_DEST}/${KERNEL_DEST}"
                cp -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ${ROOT_DEST}/${KERNEL_DEST}
            else
                echo "Kernel should be on flash"
                dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/${MTD_KERNEL}
            fi
        fi
        rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
        true
}

pkg_postrm:kernel-image () {
}

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-vuplus-${KV}:"

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
