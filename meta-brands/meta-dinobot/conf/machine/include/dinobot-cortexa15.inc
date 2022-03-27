
PACKAGECONFIG_GL_pn-qtbase = "gles2 eglfs linuxfb"
PACKAGECONFIG_FB_pn-qtbase = " "

MACHINE_FEATURES += " ${@bb.utils.contains('SOC_FAMILY', 'hisi3798mv200', 'hisichromium' , '', d)}"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"
require conf/machine/include/tune-cortexa15.inc
