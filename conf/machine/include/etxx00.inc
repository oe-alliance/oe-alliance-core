MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
	et-dvb-modules-${MACHINE} \
	"

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
	kernel-module-cdfs \
	"

# WIFI modules
module_autoload_ath9k = "ath9k"
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
module_autoload_dvb-as102 = "dvb-as102"
module_autoload_dvb-usb-a800 = "dvb-usb-a800"
module_autoload_dvb-usb-a867 = "dvb-usb-a867"
module_autoload_dvb-usb-af9005 = "dvb-usb-af9005"
module_autoload_dvb-usb-af9015 = "dvb-usb-af9015"
module_autoload_dvb-usb-af9035 = "dvb-usb-af9035"
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
module_autoload_dvb-usb-it913x = "dvb-usb-it913x"
module_autoload_dvb-usb-m920x = "dvb-usb-m920x"
module_autoload_dvb-usb-opera = "dvb-usb-opera"
module_autoload_dvb-usb-pctv452e = "dvb-usb-pctv452e"
module_autoload_dvb-usb-rtl2832 = "dvb-usb-rtl2832"
module_autoload_dvb-usb-technisat-usb2 = "dvb-usb-technisat-usb2"
module_autoload_dvb-usb-ttusb2 = "dvb-usb-ttusb2"
module_autoload_dvb-ttusb-budget = "dvb-ttusb-budget"
module_autoload_dvb-usb-nova-t-usb2 = "dvb-usb-nova-t-usb2"
module_autoload_dvb-usb-umt-010 = "dvb-usb-umt-010"
module_autoload_dvb-usb-vp702x = "dvb-usb-vp702x"
module_autoload_dvb-usb-vp7045 = "dvb-usb-vp7045"
module_autoload_smsusb = "smsdvb smsusb"

MACHINE_EXTRA_RRECOMMENDS = " \
	gst-plugin-dvbmediasink \
	"

include conf/machine/include/autoload-usbserial.inc

CHIPSET = "bcm7413"

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dtsdownmix"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

PREFERRED_PROVIDER_virtual/kernel = "linux-etxx00"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "et-blindscan-dvbs-utils"

PREFERRED_VERSION_linux-etxx00 = "3.3.0"
PREFERRED_VERSION_linux-libc-headers = "3.3"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 4096"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

IMAGEDIR ?= "${MACHINE}"

IMAGE_CMD_ubi_append = " \
	rm -rf ${IMAGE_ROOTFS}/boot/*; \
	rm -rf ${IMAGE_ROOTFS}/tmp/*; \
	mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
	cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
	mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.bin; \
	gzip -c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/kernel.bin; \
	echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
	echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
	cd ${DEPLOY_DIR_IMAGE}; \
	zip ${IMAGE_NAME}-${DATE}_usb.zip ${IMAGEDIR}/*; \
	rm -rf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.ubifs; \
	rm -Rf ${IMAGEDIR}; \
	"

TARGET_FPU = "hard"

MACHINE_FEATURES += "3dtv alsa blindscan-dvbs hdmicec switchoff textlcd wifi"

require conf/machine/include/tune-mips32.inc

MACHINE_KERNEL_PR = "r0"

EXTRA_IMAGEDEPENDS += " enigma2-plugin-extensions-et-livestream"
