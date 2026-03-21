# teamBlue image hooks
MAINTAINER = "teamBlue team"

# Override shell comfort tools: iproute2 instead of nano/mc
IMAGE_INSTALL:remove = "nano mc mc-shell mc-helpers"
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96", "", "iproute2", d)}"

# Override languages: German+English base, more on 512MB+
IMAGE_LINGUAS  = "${@bb.utils.contains_any("FLASHSIZE", "64 96 128 256", "de-de en-gb", "de-de en-gb es-es fr-fr it-it nl-nl pt-pt tr-tr", d)}"
