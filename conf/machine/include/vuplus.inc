MACHINE_BRAND = "Vu+"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
	vuplus-dvb-modules-${MACHINE} \
	"

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
	kernel-module-cdfs \
	"

MACHINE_EXTRA_RRECOMMENDS = " \
	vuplus-shutdown \
	vuplus-gst-plugin-dvbmediasink \
	"

include conf/machine/include/autoload-filesystems.inc

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls"

PREFERRED_PROVIDER_virtual/kernel = "linux-vuplus"

PREFERRED_PROVIDER_virtual/blindscan-dvbc = "vuplus-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "vuplus-blindscan-utils"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 4096 -F"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

IMAGEDIR ?= "vuplus/${UPDATE_DIR}"

IMAGE_CMD_ubi_prepend = " \
	rm -rf ${IMAGE_ROOTFS}/boot/*; \
	rm -rf ${IMAGE_ROOTFS}/tmp/*; \
	"

TARGET_FPU = "hard"

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi bootsplash"

BOOTLOGO_FILENAME = "splash_cfe_auto.bin"

require conf/machine/include/tune-mips32.inc

