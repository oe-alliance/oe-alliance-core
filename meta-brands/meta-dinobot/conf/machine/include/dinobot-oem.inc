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
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kelite', 'DINOBOT 4K+ Elite' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcombo', 'HIS4K Combo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcomboplus', 'HIS4K Combo Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'ferguson4k', 'U5 PVR' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobotu55', 'DINOBOT U55' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinoboth265', 'DINOBOT H265' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'hitube4k', '4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxone4k', 'ONE 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxx3', 'X3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashistwin', 'HIS Twin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashisc4k', 'HIS C 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'protek4kx1', '4K X1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadolprohd5', 'Pro HD5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiv2', 'Mini V2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxecohd', 'ECO TWIN HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'jdhdduo', 'HD DUO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'turing', 'TURING' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'arivatwin', 'ARIVA ATV TT' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'arivacombo', 'ARIVA ATV COMBO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipertwin', 'Viper Twin E2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipers', 'Viper S' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viper4kv20', 'Viper 4K V20' , '', d)}\
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
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kelite', 'Vivant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcombo', 'AXAS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcomboplus', 'AXAS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'ferguson4k', 'Ferguson' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobotu55', 'Vivant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinoboth265', 'Vivant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'hitube4k', 'HITUBE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxone4k', 'IZIBOX' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxx3', 'IZIBOX' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashistwin', 'AXAS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashisc4k', 'AXAS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'protek4kx1', 'PROTEK' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadolprohd5', 'ANADOL' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiv2', 'SPYCAT' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxecohd', 'IZIBOX' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'jdhdduo', 'HD DUO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'turing', 'Atto.TV' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'arivatwin', 'Ferguson' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'arivacombo', 'Ferguson' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipertwin', 'AMIKO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viperts', 'AMIKO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viper4kv20', 'AMIKO' , '', d)}\
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
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kelite', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcombo', 'mmcblk0p9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcomboplus', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'ferguson4k', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobotu55', 'mtd10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinoboth265', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'hitube4k', 'mtd10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxone4k', 'mtd10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxx3', 'mtd10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashistwin', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashisc4k', 'mmcblk0p9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'protek4kx1', 'mmcblk0p9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadolprohd5', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiv2', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxecohd', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'jdhdduo', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'turing', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'arivatwin', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'arivacombo', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipertwin', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipers', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viper4kv20', 'mmcblk0p9' , '', d)}\
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
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kelite', 'mmcblk0p13' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcombo', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcomboplus', 'mmcblk0p13' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'ferguson4k', 'mmcblk0p13' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobotu55', 'mtd11' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinoboth265', 'mtd9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'hitube4k', 'mtd11' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxone4k', 'mtd11' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxx3', 'mtd11' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashistwin', 'mtd9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashisc4k', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'protek4kx1', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadolprohd5', 'mtd9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiv2', 'mtd9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxecohd', 'mtd9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'jdhdduo', 'mtd9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'turing', 'mtd9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'arivatwin', 'mmcblk0p13' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'arivacombo', 'mmcblk0p13' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipertwin', 'mtd9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipers', 'mtd9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viper4kv20', 'mmcblk0p10' , '', d)}\
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
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kelite', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcombo', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcomboplus', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'ferguson4k', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobotu55', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinoboth265', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'hitube4k', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxone4k', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxx3', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashistwin', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashisc4k', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'protek4kx1', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadolprohd5', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiv2', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxecohd', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'jdhdduo', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'turing', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'arivatwin', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'arivacombo', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipertwin', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipers', 'no' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viper4kv20', 'no' , '', d)}\
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
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kelite', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcombo', 'axas/axascombo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashis4kcomboplus', 'axas/axascomboplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'ferguson4k', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobotu55', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinoboth265', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'hitube4k', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxone4k', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxx3', 'izibox/x3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashistwin', 'axas/axashistwin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axashisc4k', 'axas/axasc4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'protek4kx1', 'e2/update' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadolprohd5', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiv2', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iziboxecohd', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'jdhdduo', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'turing', 'atto/turing' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'arivatwin', 'e2/update' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'arivacombo', 'e2/update' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipertwin', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipers', 'dinobot/${MACHINE}' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viper4kv20', 'dinobot/${MACHINE}' , '', d)}\
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
    ${@bb.utils.contains('MACHINE_FEATURES', 'KODI', 'dinobot-opengl-${SOC_FAMILY}', '',d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'emmc', 'resizerootfs' , '', d)} \
    dinobot-partitions-${SOC_FAMILY} \
    dinobot-libs-${MACHINE} \
    dinobot-hihalt-${MACHINE} \
    dinobot-pvr \
    dinobot-lib-helper \
"

MACHINE_FEATURES += "switchoff  hdmicec blindscan-tbs alsa hbbtv-browser-webkit qtstalker HDMI \
    ${@bb.utils.contains('MACHINEBUILD', 'anadol4k', '' , ' dvb-c', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'anadol4kcombo', '' , ' dvb-c', d)} \
    "

PREFERRED_VERSION_linux-dinobot = "4.4.35"
PREFERRED_PROVIDER_virtual/egl = "dinobot-opengl-${SOC_FAMILY}" 
PREFERRED_PROVIDER_virtual/libgles1 = "dinobot-opengl-${SOC_FAMILY}"
PREFERRED_PROVIDER_virtual/libgles2 = "dinobot-opengl-${SOC_FAMILY}"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "dinobot-blindscan-${MACHINE}"

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-libav \
    ${@bb.utils.contains('MACHINEBUILD', 'iziboxx3', 'enigma2-plugin-extensions-gbipboxclient' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'u41', 'dinobot-cpu-scaling' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'u42', 'dinobot-cpu-scaling' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'u43', 'dinobot-cpu-scaling' , '', d)} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

include conf/machine/include/autoload-filesystems.inc
require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc

DVBMEDIASINK_CONFIG = "--with-h265 --with-vb8 --with-vb9 --with-spark --with-wma --with-wmv --with-pcm --with-dts --with-eac3"

PREFERRED_PROVIDER_virtual/kernel = "linux-dinobot"
PREFERRED_RPROVIDER_showiframe = "dinobot-showiframe-${MACHINE}"
PREFERRED_RPROVIDER_aio-grab = "dinobot-grab-${MACHINE}"

OEM_ARCH = "${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"

BBMASK = "meta-vuplus/recipes-graphics/"
BBMASK += "meta-fulan/recipes-graphics/"
BBMASK += "meta-vuplus/recipes-webkit/"
BBMASK += "meta-oe/recipes-qt5.14/"
