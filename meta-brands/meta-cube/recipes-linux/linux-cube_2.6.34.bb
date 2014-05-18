SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"
PR = "r2"
inherit machine_kernel_pr

KERNEL_RELEASE := "${PV}"
PV = "${KERNEL_RELEASE}"
PKGV = "${KERNEL_RELEASE}"

MACHINE_KERNEL_PR_append = ".2"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI[kernel.md5sum] = "10eebcb0178fb4540e2165bfd7efc7ad"
SRC_URI[kernel.sha256sum] = "fa395fec7de633df1cb85b6248b8f35af98380ed128a8bc465fb48bc4d252633"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

RDEPENDS_kernel-image = "updateubivolume"

SRC_URI = " \
    ${KERNELORG_MIRROR}/linux/kernel/v${PV}/linux-${PV}.tar.bz2;name=kernel \
    file://defconfig \
    file://100-arm-linux.patch \
    file://101-apollo_stb.patch \
    file://102-unionfs-2.5.4.patch \
    file://103-apollo_usb.patch \
    file://104-apollo_spi_callbackfix.patch \
    file://105-apollo_sata_fuse_fix.patch \
    file://106-kronos_stb.patch \
    file://107-apollo_linux_warning_fix.patch \
    file://108-apollo_spi_dmac_rf4cefix.patch \
    file://109-apollo_serialwrapperfix.patch \
    file://110-apollo_sfc_div_u64_fix.patch \
    file://111-apollo_mtd_define_fix.patch \
    file://112-apollo_usb_code_from_28kernel.patch \
    file://113-apollo_active_standby.patch \
    file://114-apollo_sfc32M.patch \
    file://115-apollo_sfc_jffs2_fix.patch \
    file://116-apollo_ip3106_kgdb.patch \
    file://117-apollo_sfc_jffs2_32M.patch \
    file://118-apollo_syscall.patch \
    file://119-apollo_perf_events.patch \
    file://120-apollo_cortexa9_errata.patch \
    file://121-apollo_bzImage_support.patch \
    file://122-apollo_cortexa9_freq_detect.patch \
    file://123-apollo_usb_ehci_handlers.patch \
    file://124-apollo_iic_greset_fix.patch \
    file://125-apollo-otg_redesign.patch \
    file://126-apollo_various_fixes.patch \
    file://127-apollo_squashfs_lzma.patch \
    file://128-apollo_gcc_4.5_support.patch \
    file://129-apollo_kronos_emu.patch \
    file://130-apollo_ethernet_AnDSP_changes.patch \
    file://131-apollo_thumb2_support.patch \
    file://132-apollo-mtd_devices.patch \
    file://133-apollo-numonyx_flash.patch \
    file://135-apollo-spi_gp500.patch \
    file://136-apollo-gpio_apis.patch \
    file://137-apollo_chip_rev_detect.patch \
    file://138-apollo_sfc_quad_mode.patch \
    file://139-apollo_usb_gadget_fshs.patch \
    file://140-kronos_i2c.patch \
    file://141-apollo_usb_gadget_flag_cleanup.patch \
    file://142-kronos_usb.patch \
    file://143-apollo_sfc8M.patch \
    file://144-nand_pagesize.patch \
    file://145-apollo_usb_gadget_plugfest_fixes.patch \
    file://146-apollo_usb_no_otg_usbcv_fix.patch \
    file://147-apollo_gmac0_rgmii.patch \
    file://148-apollo_usb_vid_pid_fix.patch \
    file://149-apollo_uart_isr.patch \
    file://150-apollo_macronix_sfc_quad_mode.patch \
    file://151-apollo_find_next_zero_bit.patch \
    file://152-apollo_usb_host_tpl.patch \
    file://153-apollo_nand4k.patch \
    file://154-apollo_network_config.patch \
    file://155-apollo_bzImage_lzma.patch \
    file://156-apollo_sdio_pci_support.patch \
    file://157-apollo_linux_dvb_extension.patch \
    file://158-kronos_bzImage.patch \
    file://159-apollo_onfi_nand_support.patch \
    file://160-apollo_usb_reset_fix.patch \
    file://161-apollo_sfc_macronix_dma.patch \
    file://162-apollo_arm_errata.patch \
    file://163-apollo_nand_onfi_chipsize.patch \
    file://164-kronos_nand.patch \
    file://165-kronos_sdio.patch \
    file://166-apollo_nand_block_erase_err.patch \
    file://167-apollo_mtd_nand_bbt.patch \
    file://168-kronos_mmioaddr.patch \
    file://169-kronos_gpioconfig.patch \
    file://170-kronos_nor_dma_config.patch \
    file://171-kronos_usb_bringup.patch \
    file://172-kronos_gmac.patch \
    file://173-kronos_gpio.patch \
    file://174-kronos_sfc_ext_id.patch \
    file://175-kronos_l2cache.patch \
    file://176-kronos_nand_bringup.patch \
    file://177-apollo_uart_dma.patch \
    file://178-kronos_sata_bringup.patch \
    file://179-kronos_spi_bringup.patch \
    file://181-krome_stb.patch \
    file://182-kronos_active_stby.patch \
    file://183-apollo_sdio_versionfix.patch \
    file://184-kronos_sdio_bringup.patch \
    file://185-kronos_mmc_subsys_2.6.39.1.patch \
    file://186-kronos_rtc.patch \
    file://187-kronos_active_standby_irq_fix.patch \
    file://188-kronos_sdio_wr_fix.patch \
    file://189-apollo_dcs_bus_ntwk_driver.patch \
    file://190-apollo_kronos_eth_leak.patch \
    file://191-kronos_splash_screen.patch \
    file://192-apollo_nand_224B_oob.patch \
    file://193-krome_affinity_symbol_export.patch \
    file://194-krome_ep_build.patch \
    file://195-krome_ep_bringup.patch \
    file://196-krome_gmac0_base.patch \
    file://197-krome_active_stby.patch \
    file://198-krome_sdio_bringup.patch \
    file://199-krome_a9_clk_fix.patch \
    file://200-krome_dualcore.patch \
    file://202-kronos_krome_nand_ecc.patch \
    file://203-krome_uart_fix.patch \
    file://204-kronos-krome_sfc_quadread.patch \
    file://205-krome_gmac_timer_fix.patch \
    file://206-apollo_gpl_header.patch \
    file://207-krome_gpio.patch \
    file://208-sfc_micron_quad_mode.patch \
    file://209-i2c_locking.patch \
    file://210-kronos_sddata4_7_pins_disable.patch \
    file://211-nand_oob_write.patch \
    file://212-kronos-krome_detect_arm_freq.patch \
    file://213-kronos-krome_nand_224oob.patch \
    file://214-kronos-krome_vmalloc_increase.patch \
    file://215-kronos_active_standby_fix.patch \
    file://216-krome_sdio_cdwp_cfg.patch \
    file://217-kronos-krome_nand_read_uldr.patch \
    file://218-kronos-krome_highmem_support.patch \
    file://219-kronos-krome_rev.patch \
    file://220-kronos-krome_max_vmalloc_area.patch \
    file://221-kronos-krome_gmac_lnkstatusint.patch \
    file://222-kronos_print_cortexa9_freq.patch \
    file://223-apollo_sata_coherency_issue_fix.patch \
    file://224-kronosrevb_krome_splash.patch \
    file://225-krome-balboa.patch \
    file://226-krome_print_arm_freq.patch \
    file://227-sd_fallback_normalspeed.patch \
    file://228-kronos-krome_8k_nand.patch \
    file://229-splashlogo_sfc_mx_spi.patch \
    file://230-splashlogo.patch \
    file://231-krome_spi_fix.patch \
    file://232-krome_gmac_fix.patch \
    file://233-krome_splash_fix.patch \
    file://234-ethtool_fix.patch \
    file://235-l2cache_errata.patch \
    file://236-usb_sata_coherency_issue_fix.patch \
    file://237-kronos-krome_mmc.patch \
    file://238-kronos_sata_phy_tuning.patch \
    file://239-kore3_stb.patch \
    file://240-krome_dualcore_mod.patch \
    file://241_krome_dual_sd.patch \
    file://242_moca_loopback.patch \
    file://243-kronos-krome_mem_barriers.patch \
    file://244-usb_sata_coherancy_issue_fix_mod.patch \
    file://245-console_corruption_fix.patch \
    file://246-gmac_flowcontrol.patch \
    file://247-moca_SIOCTOGEXTCLKEN_add.patch \
    file://248-apollo_mem_barriers.patch \
    file://249-apollo_sata_coherancy_issue_fix_mod.patch \
    file://250-gmac_packetloss_memoryleak_fix.patch \
    file://251-arm_user_cache_flush_fix.patch \
    file://252-en256x_standby.patch \
    file://253-splash_newlogo.patch \
    file://254-gmac-dma_fixes.patch \
    file://255-kore3_bringup.patch \
    file://256-sd_suspend_resume.patch \
    file://257-sfc_write_sr_fix.patch \
    file://258-moca_loopback_ret.patch \
    file://259-mmc_csd_struct_v3.patch \
    file://260-kore3_bringup2.patch \
    file://261-sfc_32b_mx_spa.patch \
    file://262-ephy_powerdown.patch \
    file://263-kronos-krome_splash_fix.patch \
    file://300-dmxdev-unblock-read-on-ioctl.patch \
	"

S = "${WORKDIR}/linux-${PV}"

inherit kernel

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGETYPE = "zImage"
KERNEL_IMAGEDEST = "/tmp"

FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}"

do_configure_prepend() {
    oe_machinstall -m 0644 ${WORKDIR}/defconfig ${S}/.config
    oe_runmake oldconfig
}

kernel_do_install_append() {
    cp include/generated/asm-offsets.h $kerneldir/include/generated/asm-offsets.h
    install -d ${D}${KERNEL_IMAGEDEST}
    install -m 0755 ${KERNEL_OUTPUT} ${D}${KERNEL_IMAGEDEST}
}

pkg_postinst_kernel-image() {
    if [ -f ${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} ]; then
        IMAGENAME=""
        set -- $(cat /proc/cmdline)
        for x in "$@"; do
            case "$x" in
                root=*)
                IMAGENAME="${x#root=ubi0:rootfs_}"
                ;;
            esac
        done
        if [ ! -z "${IMAGENAME}" ]; then
           updateubivolume kernel_${IMAGENAME} ${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
        fi
        rm -f ${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    fi
    true
}
