SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"
KV = "3.13.5"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = "oea4.1-r1"

SRCREV = ""

SRC_URI[md5sum] = "19e9956653437b99b4fa6ec3e16a3e99"
SRC_URI[sha256sum] = "ef7fb307582ff243aacff8a13025fe028634aaf650ada309991ae03622962f61"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

KERNEL_CONFIG = "${@bb.utils.contains("MACHINE_FEATURES", "dvbproxy", "defconfig_proxy", "defconfig", d)}"

SRC_URI = "http://archive.vuplus.com/download/kernel/stblinux-${KV}.tar.bz2 \
    file://${KERNEL_CONFIG} \
    file://rt2800usb_fix_warn_tx_status_timeout_to_dbg.patch \
    file://add-dmx-source-timecode.patch \
    file://af9015-output-full-range-SNR.patch \
    file://af9033-output-full-range-SNR.patch \
    file://as102-adjust-signal-strength-report.patch \
    file://as102-scale-MER-to-full-range.patch \
    file://cxd2820r-output-full-range-SNR.patch \
    file://dvb-usb-dib0700-disable-sleep.patch \
    file://dvb_usb_disable_rc_polling.patch \
    file://it913x-switch-off-PID-filter-by-default.patch \
    file://tda18271-advertise-supported-delsys.patch \
    file://mxl5007t-add-no_probe-and-no_reset-parameters.patch \
    file://linux-tcp_output.patch \
    file://linux-3.13-gcc-4.9.3-build-error-fixed.patch \
    file://kernel-add-support-for-gcc-5.patch \
    file://rtl8712-fix-warnings.patch \
    file://0001-Support-TBS-USB-drivers-3.13.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://0002-cp1emu-boolean-expression.patch \
    file://0003-log2-stop-text-relocations.patch \
    file://0004-page_alloc-free_area_init_nodes.patch \
    file://0005-bmpis_vec-assembler.patch \
    file://0006-libata-core-ata_timing_quantize.patch \
    file://0007-namespace.patch \
    file://0008-rmap.patch \
    file://0009-em28xx-Kconfig.patch \
    file://0010-netmisc.patch \
    file://0011-rtnetlink.patch \
    file://0012-fscache-object.patch \
    file://0013-xattr.patch \
    file://0014-net-sysfs.patch \
    file://0015-mtd_blkdevs.patch \
    file://0016-fuse-uninitialized-var.patch \
    file://0017-arp.patch \
    file://0018-af_packet.patch \
    file://0019-ping-ping_v4_seq_ops.patch \
    file://0020-ehci-ohci-brcm-usb_hcd_brcm_match.patch \
    file://0021-extents_status.patch \
    file://0022-rtlwifi-efuse.patch \
    file://0023-xfs_bmap_util.patch \
    file://0024-ac97_codec.patch \
    file://0026-sortextable-define-relocs_size.patch \
    file://0027-menu-jump-declaration.patch \
    file://0028-drxd_hard.patch \
    file://0029-stb6100.patch \
    file://0030-brcm80211-brcmfmac.patch \
    file://0031-prism2fw.patch \
    file://0032-mac80211_hwsim.patch \
    file://0033-r815x.patch \
    file://0034-dib0090.patch \
    file://0035-rtl8225.patch \
    file://0036-garmin_gps.patch \
    file://0038-r820t.patch \
    file://0039-libertas_tf-main.patch \
    file://0040-tbs-usb.patch \
    file://0041-ov519.patch \
    file://0042-usbnet.patch \
    file://0043-usbvision-core.patch \
    file://0044-ath9k-eeprom.patch \
    file://0045-ieee80211.patch \
    file://blindscan2.patch \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvbproxy", "file://linux_dvb_adapter.patch;patch=1;pnum=1", "", d)} \
    file://kernel-add-support-for-gcc6.patch \
    file://genksyms_fix_typeof_handling.patch \
    file://kernel-add-support-for-gcc7.patch \
    "

SRC_URI_append_vuduo2 = "file://brcm_s3_wol.patch;patch=1;pnum=1 "
SRC_URI_append_vusolose = "file://brcm_s3_wol.patch;patch=1;pnum=1 \
                          file://linux_mtd_bbt_maxblock.patch \
"
SRC_URI_append_vusolo2 = "file://linux-bcm_ethernet.patch;patch=1;pnum=1 "
 
S = "${WORKDIR}/linux"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

FILES_${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

do_configure_prepend() {
    if [ -e ${WORKDIR}/defconfig_proxy ]; then
    mv ${WORKDIR}/defconfig_proxy ${WORKDIR}/defconfig
    fi
}

kernel_do_install_append() {
    ${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    rm ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst_kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase  /dev/${MTD_KERNEL} 0 0
            nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
            rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    true
}

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
