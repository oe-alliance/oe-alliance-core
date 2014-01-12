MACHINE_BRAND = "Mixos"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
	ebox-dvb-modules-${MACHINE} \
	ebox-shutdown \
	"
	
MACHINE_EXTRA_RRECOMMENDS = " \
	gst-plugin-dvbmediasink \
	"

include conf/machine/include/autoload-filesystems.inc

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"
DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts"

PREFERRED_PROVIDER_virtual/kernel = "linux-ebox"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "null"


MACHINE_FEATURES += "alsa dvbapi5 usbhost switchoff wifi 3dtv hdmicec boxmodel"

require conf/machine/include/tune-mips32.inc

