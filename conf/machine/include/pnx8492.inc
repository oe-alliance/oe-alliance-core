MACHINE_BRAND = "E2BMC"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
	e2bmc-dvb-modules-${MACHINE} \
	"
	
DVBPROVIDER = "kernel"

MACHINE_EXTRA_RRECOMMENDS = " \
	gst-plugin-dvbmediasink \
	"
	
include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dtsdownmix --with-eac3"

include conf/machine/include/imgzip.inc

IMAGE_DEVICE_TABLES = ""

PREFERRED_VERSION_wpa-supplicant = "0.5.8"
PREFERRED_VERSION_rtl8192cu = "3.3.0"

PREFERRED_PROVIDER_virtual/kernel = "linux-cube"
PREFERRED_PROVIDER_virtual/egl = "opengl"

PREFERRED_VERSION_linux-cube = "2.6.34"
PREFERRED_VERSION_linux-libc-headers = "2.6.31"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

TARGET_ARCH = "arm"
TUNE_FEATURES = "cortexa9 vfp"
include conf/machine/include/tune-cortexa9.inc
TUNE_CCARGS += "-mfpu=vfpv3-d16"

IMAGE_FSTYPES ?= "imgzip256"

# magic value to avoid flashing an image which was intended to run on different hardware
CREATEIMAGE_MAGIC = "0x00109500"


MACHINE_FEATURES += "3dtv alsa hdmicec switchoff textlcd wifi dvbapi5 usbhost wifiusblegacy legacykernel"
