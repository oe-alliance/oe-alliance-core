MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
	technomate-dvb-modules \
	"

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
	kernel-module-cdfs \
	"

MACHINE_EXTRA_RRECOMMENDS = " \
	vuplus-shutdown \
	gst-plugin-dvbmediasink \
	"

include conf/machine/include/autoload-wifi.inc
include conf/machine/include/autoload-dvb-usb.inc
include conf/machine/include/autoload-usbserial.inc

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls"

PREFERRED_PROVIDER_virtual/kernel = "linux-technomate"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "vuplus-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "vuplus-blindscan-utils"

PREFERRED_VERSION_linux-${MACHINE} = "3.5.3"
PREFERRED_VERSION_linux-libc-headers = "3.5"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 4096 -F"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

IMAGE_CMD_ubi_prepend = " \
	rm -rf ${IMAGE_ROOTFS}/boot/*; \
	rm -rf ${IMAGE_ROOTFS}/tmp/*; \
	"

IMAGE_CMD_ubi_append = " \
	mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
	cp ${DEPLOY_DIR_IMAGE}/splash.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bmp; \
	mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/oe_rootfs.bin; \
	gzip -f -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/oe_kernel.bin; \
	echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
	cd ${DEPLOY_DIR_IMAGE}; \
	zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
	rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
	rm -Rf update; \
	"

TARGET_FPU = "hard"

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi"

require conf/machine/include/tune-mips32.inc
