# OE-Alliance Distro Image - Enigma2 with distro branding
# Used by oea-enigma2-image.bb and oea-image.bb
# Distro-specific overrides via optional ${DISTRO}-image-hooks.inc

require oea-image-common.inc
require oea-image-enigma2.inc

IMAGE_INSTALL += "\
    packagegroup-oea-enigma2-plugins \
    packagegroup-oea-distro-${DISTRO} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvbc-only", "", "enigma2-plugin-settings-defaultsat", d)} \
    "

# Shell comfort: nano on 128MB+, mc on 256MB+
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96", "", "nano", d)}"
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "mc mc-shell mc-helpers", d)}"

# Languages: de+fr on 256MB+, more on 512MB+
IMAGE_LINGUAS  = "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "de-de fr-fr", d)}"
IMAGE_LINGUAS += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128 256", "", "es-es it-it nl-nl pt-pt", d)}"

# Optional distro-specific hooks (loaded only if file exists)
include ../../recipes-distros/${DISTRO}/image/${DISTRO}-image-hooks.inc
