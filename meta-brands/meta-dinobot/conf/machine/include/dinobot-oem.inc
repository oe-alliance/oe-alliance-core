# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "dinobot"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'anadol4kcombo', 'Combo 4K UHD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadol4kv2', 'ECO 4K UHD V2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadol4k', 'ECO´4K UHD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kl', 'DINOBOT 4KL' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4k', 'DINOBOT 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox4k', 'mediabox 4k M7L' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kse', 'DINOBOT 4KSE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kplus', 'DINOBOT 4K Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kmini', 'DINOBOT 4K mini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kpro', 'DINOBOT 4K Pro' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcombo', 'HIS4K Combo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcomboplus', 'HIS4K Combo Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'ferguson4k', 'U5 PVR' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobotu55', 'DINOBOT U55' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinoboth265', 'DINOBOT H265' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'hitube4k', '4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxone4k', 'ONE 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashistwin', 'HIS Twin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashisc4k', 'HIS C 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'protek4kx1', '4K X1' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'anadol4kcombo', 'ANADOL' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadol4kv2', 'ANADOL' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadol4k', 'ANADOL' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kl', 'Vivant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4k', 'Vivant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox4k', 'JEPSSEN' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kse', 'Vivant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kplus', 'Vivant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kmini', 'Vivant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kpro', 'Vivant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcombo', 'AXAS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcomboplus', 'AXAS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'ferguson4k', 'Ferguson' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobotu55', 'Vivant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinoboth265', 'Vivant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'hitube4k', 'HITUBE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxone4k', 'IZIBOX' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashistwin', 'AXAS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashisc4k', 'AXAS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'protek4kx1', 'PROTEK' , '', d)}\
"

MTD_KERNEL = "\
${@bb.utils.contains('MACHINEBUILD', 'anadol4kcombo', 'mmcblk0p9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadol4kv2', 'mmcblk0p9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadol4k', 'mmcblk0p9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kl', 'mmcblk0p9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4k', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox4k', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kse', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kplus', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kmini', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kpro', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcombo', 'mmcblk0p9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcomboplus', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'ferguson4k', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobotu55', 'mtd10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinoboth265', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'hitube4k', 'mtd10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxone4k', 'mtd10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashistwin', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashisc4k', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'protek4kx1', 'mmcblk0p9' , '', d)}\
"

MTD_ROOTFS = "\
${@bb.utils.contains('MACHINEBUILD', 'anadol4kcombo', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadol4kv2', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadol4k', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kl', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4k', 'mmcblk1p1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox4k', 'mmcblk1p1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kse', 'mmcblk0p13' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kplus', 'mmcblk0p13' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kmini', 'mmcblk0p13' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kpro', 'mmcblk0p13' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcombo', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcomboplus', 'mmcblk0p13' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'ferguson4k', 'mmcblk0p13' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobotu55', 'mtd11' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinoboth265', 'mtd9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'hitube4k', 'mtd11' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxone4k', 'mtd11' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashistwin', 'mtd9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashisc4k', 'mmcblk0p13' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'protek4kx1', 'mmcblk0p10' , '', d)}\
"

SDCARDIMG = "\
${@bb.utils.contains('MACHINEBUILD', 'anadol4kcombo', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadol4kv2', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadol4k', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kl', 'yes' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4k', 'yes' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox4k', 'yes' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kse', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kplus', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kmini', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kpro', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcombo', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcomboplus', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'ferguson4k', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobotu55', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinoboth265', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'hitube4k', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxone4k', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashistwin', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashisc4k', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'protek4kx1', 'no' , '', d)}\
"

IMAGEDIR = "\
${@bb.utils.contains('MACHINEBUILD', 'anadol4kcombo', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadol4kv2', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadol4k', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kl', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4k', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox4k', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kse', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kplus', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kmini', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kpro', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcombo', 'axas/axascombo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcomboplus', 'axas/axascomboplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'ferguson4k', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobotu55', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinoboth265', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'hitube4k', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxone4k', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashistwin', 'axas/axashistwin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashisc4k', 'axas/axasc4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'protek4kx1', 'dinobot/${MACHINE}' , '', d)}\
"

KERNEL_MODULE_AUTOLOAD += "xhci-plat-hcd"
KERNEL_MODULE_AUTOLOAD += "ehci-platform"
KERNEL_MODULE_AUTOLOAD += "ohci-platform"
KERNEL_MODULE_AUTOLOAD += "cfg80211"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    dinobot-dvb-modules-${MACHINE} \
    kernel-module-xhci-plat-hcd \
    kernel-module-ehci-platform \
    kernel-module-ohci-platform \
    ${@bb.utils.contains('MACHINE_FEATURES', 'KODI', 'dinobot-opengl-${MACHINE}', '',d)} \
    dinobot-libs-${MACHINE} \
    dinobot-hihalt-${MACHINE} \
    dinobot-pvr \
    dinobot-lib-helper \
"

MACHINE_FEATURES += "switchoff  hdmicec textlcd skins1080 blindscan-tbs emmc mmc alsa hbbtv-browser-webkit qtstalker HDMI \
    ${@bb.utils.contains('MACHINEBUILD', 'anadol4k', '' , ' dvb-c', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'anadol4kcombo', '' , ' dvb-c', d)} \
    "

PREFERRED_VERSION_linux-dinobot = "4.4.35"
PREFERRED_VERSION_linux-libc-headers = "4.4"
PREFERRED_PROVIDER_virtual/egl = "dinobot-opengl-${MACHINE}" 
PREFERRED_PROVIDER_virtual/libgles1 = "dinobot-opengl-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles2 = "dinobot-opengl-${MACHINE}"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "dinobot-blindscan-${MACHINE}"
PREFERRED_VERSION_ffmpeg = "3.4.2"

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-libav \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

include conf/machine/include/autoload-filesystems.inc
require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc

DVBMEDIASINK_CONFIG = "--with-h265 --with-vb8 --with-vb9 --with-spark --with-opus --with-wma --with-wmv --with-pcm --with-dts --with-eac3"

PREFERRED_PROVIDER_virtual/kernel = "linux-dinobot"
PREFERRED_RPROVIDER_showiframe = "dinobot-showiframe-${MACHINE}"
PREFERRED_RPROVIDER_aio-grab = "dinobot-grab-${MACHINE}"

OEM_ARCH = "${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"

BBMASK = "meta-vuplus/recipes-graphics/"
BBMASK += "meta-fulan/recipes-graphics/"
BBMASK += "meta-vuplus/recipes-webkit/"
