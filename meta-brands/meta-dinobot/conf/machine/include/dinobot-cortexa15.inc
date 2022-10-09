
PACKAGECONFIG_GL:pn-qtbase = "gles2 eglfs linuxfb"
PACKAGECONFIG_FB:pn-qtbase = " "

MACHINE_FEATURES += " mali ${@bb.utils.contains('SOC_FAMILY', 'hisi3798mv200', 'hisichromium' , '', d)}"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"
require conf/machine/include/arm/armv7a/tune-cortexa15.inc
