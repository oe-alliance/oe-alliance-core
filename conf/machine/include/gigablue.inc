MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
	gigablue-dvb-modules-${MACHINE} \
	"

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
	kernel-module-cdfs \
	"

MACHINE_EXTRA_RRECOMMENDS = " \
	gst-plugin-dvbmediasink \
	gigablue-cfe \
	"

include conf/machine/include/autoload-wifi.inc
include conf/machine/include/autoload-dvb-usb.inc
include conf/machine/include/autoload-usbserial.inc

DVBMEDIASINK_CONFIG = "--with-wmv"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls"

PREFERRED_PROVIDER_virtual/kernel = "linux-gigablue"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

TARGET_FPU = "hard"

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff usbhost wifi ci"

require conf/machine/include/tune-mips32.inc

