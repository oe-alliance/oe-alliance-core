inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".22"

PATCHREV = "9321e2fc1ab885c955d9134f4c9b5c9b5a4e56b3"
PATCHLEVEL = "28"

SRC_URI = " \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-${PV}.tar.xz;name=kernel \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-${PV}.${PATCHLEVEL}.xz;apply=yes;name=stable-patch \
    http://dreamboxupdate.com/download/kernel-patches/linux-dreambox-${PV}-${PATCHREV}.patch.xz;apply=yes;name=dream-patch \
    file://0001-dm900-enable-support-for-rtl8192ce-based-usb-wlan-st.patch \
    file://0001-removed-Abocom-WLAN-USB-stick-0x07b8-0x8188-from-rtl.patch \
    file://0001-DVB-dvb_ringbuffer-add-missing-smp-write-barrier.patch \
    file://0001-dreambox-disable-kernel-wake-timer-support-.-it-is-h.patch \
    file://0001-dm900-update-kernel-config.patch \
    file://0001-dm900-workarounds-for-boot-and-shutdown-problems-wit.patch \
    file://0001-cifs-Allow-directIO-read-write-during-cache-strict.patch \
    file://defconfig \
    file://0001-Support-TBS-USB-drivers.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://blindscan2.patch \
"

SRC_URI[kernel.md5sum] = "b621207b3f6ecbb67db18b13258f8ea8"
SRC_URI[kernel.sha256sum] = "61558aa490855f42b6340d1a1596be47454909629327c49a5e4e10268065dffa"
SRC_URI[stable-patch.md5sum] = "502a4ee34af04e9b9e375e254f7b9a8f"
SRC_URI[stable-patch.sha256sum] = "e3c79a30ac959c84c329be5461da88a5c79c6463da30d376c27bb103aee79b51"
SRC_URI[dream-patch.md5sum] = "93e8592320849b0aa31825ee704c15f4"
SRC_URI[dream-patch.sha256sum] = "4d3197b9b8e8cdddb99fd2ce3d0429978975c5f38ef757e35b6c020278b1683a"

require linux-dreambox4.inc
require recipes-kernel/linux/linux-dtb.inc

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

CMDLINE = "bmem=640M@384M bmem=384M@2048M console=ttyS0,1000000 root=/dev/mmcblk0p2 rootwait rootfstype=ext4"

DEFCONFIG = "${MACHINE}"

BRCM_PATCHLEVEL = "1.15"

LINUX_VERSION = "${PV}-${BRCM_PATCHLEVEL}-${MACHINE}"
KERNEL_IMAGETYPE = "zImage"
KERNEL_DEVICETREE = "dreambox-${MACHINE}.dtb"

do_rm_work() {
}
