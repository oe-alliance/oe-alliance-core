MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
	vuplus-dvb-modules-${MACHINE} \
	"

DVBPROVIDER = "kernel"

#WiFi
module_autoload_ath9k_htc = "ath9k_htc"
module_autoload_carl9170 = "carl9170"
module_autoload_prism2_usb = "prism2_usb"
module_autoload_rt73usb = "rt73usb"
module_autoload_rt2500usb = "rt2500usb"
module_autoload_rt2800usb = "rt2800usb"
module_autoload_r8192u_usb = "r8192u_usb"
module_autoload_rtl8192cu = "rtl8192cu"
module_autoload_rtl8187 = "rtl8187"
module_autoload_r8712u = "r8712u"
module_autoload_w35und = "w35und"
module_autoload_zd1211rw = "zd1211rw"

# DVB modules
module_autoload_em28xx-dvb = "em28xx-dvb"
module_autoload_dvb-usb-a800 = "dvb-usb-a800"
module_autoload_dvb-usb-a867 = "dvb-usb-a867"
module_autoload_dvb-usb-af9005 = "dvb-usb-af9005"
module_autoload_dvb-usb-af9015 = "dvb-usb-af9015"
module_autoload_dvb-usb-anysee = "dvb-usb-anysee"
module_autoload_dvb-usb-au6610 = "dvb-usb-au6610"
module_autoload_dvb-usb-az6027 = "dvb-usb-az6027"
module_autoload_dvb-usb-ce6230 = "dvb-usb-ce6230"
module_autoload_dvb-usb-cxusb = "dvb-usb-cxusb"
module_autoload_dvb-usb-cinergyT2 = "dvb-usb-cinergyT2"
module_autoload_dvb-usb-digitv = "dvb-usb-digitv"
module_autoload_dvb-usb-dib0700 = "dvb-usb-dib0700"
module_autoload_dvb-usb-dibusb-mb = "dvb-usb-dibusb-mb"
module_autoload_dvb-usb-dibusb-mc = "dvb-usb-dibusb-mc"
module_autoload_dvb-usb-dtv5100 = "dvb-usb-dtv5100"
module_autoload_dvb-usb-dtt200u = "dvb-usb-dtt200u"
module_autoload_dvb-usb-dw2102 = "dvb-usb-dw2102"
module_autoload_dvb-usb-ec168 = "dvb-usb-ec168"
module_autoload_dvb-usb-friio = "dvb-usb-friio"
module_autoload_dvb-usb-gl861 = "dvb-usb-gl861"
module_autoload_dvb-usb-gp8psk = "dvb-usb-gp8psk"
module_autoload_dvb-usb-it9135 = "dvb-usb-it9135"
module_autoload_dvb-usb-m920x = "dvb-usb-m920x"
module_autoload_dvb-usb-opera = "dvb-usb-opera"
module_autoload_dvb-usb-rtl2832 = "dvb-usb-rtl2832"
module_autoload_dvb-usb-technisat-usb2 = "dvb-usb-technisat-usb2"
module_autoload_dvb-usb-ttusb2 = "dvb-usb-ttusb2"
module_autoload_dvb-ttusb-budget = "dvb-ttusb-budget"
module_autoload_dvb-usb-nova-t-usb2 = "dvb-usb-nova-t-usb2"
module_autoload_dvb-usb-umt-010 = "dvb-usb-umt-010"
module_autoload_dvb-usb-vp702x = "dvb-usb-vp702x"
module_autoload_dvb-usb-vp7045 = "dvb-usb-vp7045"
module_autoload_smsusb = "smsdvb smsusb"

include conf/machine/include/autoload-usbserial.inc

MACHINE_EXTRA_RRECOMMENDS = " \
	vuplus-shutdown \
	gst-plugin-dvbmediasink \
	ntfs-3g \
	"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls"

PREFERRED_VERSION_linux-${MACHINE} = "3.1.1"
PREFERRED_VERSION_linux-libc-headers = "3.1"

PREFERRED_PROVIDER_virtual/kernel = "linux-${MACHINE}"

PREFERRED_PROVIDER_virtual/blindscan-dvbc = "vuplus-blindscan-utils-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "vuplus-blindscan-utils-${MACHINE}"

IMAGE_FSTYPES ?= "ubi"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 4096 -F"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

IMAGEDIR ?= "${MACHINE}"
EXTRA_IMAGECMD_COMPAT = " --eraseblock=0x20000 -n -l "

IMAGE_CMD_ubi_prepend = " \
        mkfs.jffs2 --root=${IMAGE_ROOTFS}/boot --faketime \
        --disable-compressor=lzo --compression-mode=size \
        --output=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
        ${EXTRA_IMAGECMD_COMPAT}; rm -rf ${IMAGE_ROOTFS}/boot/*; \
        "

IMAGE_CMD_ubi_append = " \
        mkdir -p ${DEPLOY_DIR_IMAGE}/vuplus/${UPDATE_DIR}; \
        cp ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/vuplus/${UPDATE_DIR}/root_cfe_auto.jffs2; \
        cp ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 ${DEPLOY_DIR_IMAGE}/vuplus/${UPDATE_DIR}/boot_cfe_auto.jffs2; \
        gzip -f -c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/vuplus/${UPDATE_DIR}/kernel_cfe_auto.bin; \
        cd ${DEPLOY_DIR_IMAGE}; \
        zip ${IMAGE_NAME}_usb.zip vuplus/${UPDATE_DIR}/*; \
        rm -rf vuplus; \
        "

TARGET_FPU = "hard"

MACHINE_FEATURES += "alsa usbhost wifi 3dtv switchoff osdposition hdmicec"

require conf/machine/include/tune-mips32.inc
