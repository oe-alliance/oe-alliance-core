PRECOMPILED_ARCH ?= "${MACHINE}"
PRECOMPILED_VERSION ?= "${PV}"

SRC_URI = "https://source.mynonpublic.com/dreambox/secondstage-${PRECOMPILED_ARCH}-${PRECOMPILED_VERSION}.bin"

S = "${WORKDIR}"

require dreambox-secondstage-base.inc
