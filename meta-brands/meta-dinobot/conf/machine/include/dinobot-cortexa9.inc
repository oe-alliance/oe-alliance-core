PACKAGECONFIG_GL:pn-qtbase = "no-opengl linuxfb"
PACKAGECONFIG_FB:pn-qtbase = " "
TARGET_ARCH = "arm"
DEFAULTTUNE  = "cortexa9hf-neon"
require conf/machine/include/tune-cortexa9.inc
