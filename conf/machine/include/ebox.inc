MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
	ebox-dvb-modules-${MACHINE} \
	ebox-shutdown \
	"
	
MACHINE_EXTRA_RRECOMMENDS = " \
	gst-plugin-dvbmediasink \
	"

include conf/machine/include/autoload-wifi.inc
include conf/machine/include/autoload-usbserial.inc

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

DVBMEDIASINK_CONFIG = "--with-dtsdownmix --with-limited-mpeg4v2"

PREFERRED_PROVIDER_virtual/kernel = "linux-ebox"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "null"

TARGET_FPU = "soft"

MACHINE_FEATURES += "alsa dvbapi5 usbhost switchoff ci wifi wifiusblegacy textlcd"

require conf/machine/include/tune-mips32.inc

