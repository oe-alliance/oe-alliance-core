SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPL-2.0-only"
SECTION = "kernel"

MODULE = "linux-3.14.28"

COMPATIBLE_MACHINE = "^(vusolo4k|vuultimo4k|vuuno4k)$"

MACHINE_KERNEL_PR:append = "oea4.2-r4"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

inherit kernel machine_kernel_pr

KERNELSRC = "stblinux-3.14-1.12.tar.bz2"
KERNELSRC:vusolo4k = "stblinux-3.14-1.8.tar.bz2"

SRC_URI[vusolo4k.md5sum] = "1a62540fdd7f23aa2c1ebf119ca71e91"
SRC_URI[vusolo4k.sha256sum] = "a464515d14dda772047ddba7900858f535057294903341e2c1774009eaf95005"
SRC_URI[vuuno4k.md5sum] = "2a3a0a7e5cd2a1392f1a26790d1cd8bf"
SRC_URI[vuuno4k.sha256sum] = "8284670c28a4dad9e94752b38d37a4368f27ce15e671653a3e2ac83915f37db1"
SRC_URI[vuultimo4k.md5sum] = "2a3a0a7e5cd2a1392f1a26790d1cd8bf"
SRC_URI[vuultimo4k.sha256sum] = "8284670c28a4dad9e94752b38d37a4368f27ce15e671653a3e2ac83915f37db1"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "https://source.mynonpublic.com/vuplus/release/kernel/${KERNELSRC};name=${MACHINE} \
    file://defconfig \
    file://bcm_genet_disable_warn.patch \
    file://linux_dvb-core.patch \
    file://rt2800usb_fix_warn_tx_status_timeout_to_dbg.patch \
    file://usb_core_hub_msleep.patch \
    file://rtl8712_fix_build_error.patch \
    file://kernel-add-support-for-gcc6.patch \
    file://kernel-add-support-for-gcc7.patch \
    file://kernel-add-support-for-gcc8.patch \
    file://kernel-add-support-for-gcc9.patch \
    file://kernel-add-support-for-gcc10.patch \
    file://kernel-add-support-for-gcc11.patch \
    file://kernel-add-support-for-gcc12.patch \
    file://kernel-add-support-for-gcc13.patch \
    file://kernel-add-support-for-gcc14.patch \
    file://build-with-gcc12-fixes.patch \
    file://0001-Support-TBS-USB-drivers.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://blindscan2.patch \
    file://genksyms_fix_typeof_handling.patch \
    file://0001-tuners-tda18273-silicon-tuner-driver.patch \
    file://01-10-si2157-Silicon-Labs-Si2157-silicon-tuner-driver.patch \
    file://02-10-si2168-Silicon-Labs-Si2168-DVB-T-T2-C-demod-driver.patch \
    file://0003-cxusb-Geniatech-T230-support.patch \
    file://CONFIG_DVB_SP2.patch \
    file://dvbsky.patch \
    file://rtl2832u-2.patch \
    file://0004-log2-give-up-on-gcc-constant-optimizations.patch \
    file://0005-uaccess-dont-mark-register-as-const.patch \
    file://0006-makefile-disable-warnings.patch \
    file://move-default-dialect-to-SMB3.patch \
    file://fix-multiple-defs-yyloc.patch \
    file://fix-linker-issue-undefined-reference.patch \
    file://linux3.4-ARM-8933-1-replace-Sun-Solaris-style-flag-on-section.patch \
    file://fix-build-with-binutils-2.41.patch \
    "

export KCFLAGS = " -Wno-error=incompatible-pointer-types \
                   -Wno-error=address-of-packed-member \
                   -Wno-error=unused-result \
                   -Wno-error=format-overflow \
                   -Wno-error=stringop-overflow \
                   -Wno-error=unused-variable \
                   -Wno-error=int-conversion \
                   -Wno-error=array-parameter \
                   -Wno-error=unused-function \
                   -Wno-error=stringop-overread \
                   -Wno-error=unused-const-variable \
                   -Wno-error=maybe-uninitialized \           
"

SRC_URI:append:vuuno4k = " file://linux_prevent_usb_dma_from_bmem.patch"
SRC_URI:append:vusolo4k = " file://linux_rpmb_not_alloc.patch file://fix_mmc_3.14.28-1.10.patch"
SRC_URI:append:vuultimo4k = " file://bcmsysport_3.14.28-1.12.patch file://linux_prevent_usb_dma_from_bmem.patch"

SRC_URI:append = "${@bb.utils.contains("MACHINE_FEATURES", "dvbproxy", " file://linux_dvb_adapter.patch;striplevel=1", "", d)}"

S = "${WORKDIR}/linux"
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
        oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}"
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
