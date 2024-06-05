PRECOMPILED_ARCH ?= "${MACHINE}"
PRECOMPILED_VERSION ?= "${PV}"

SRC_URI = "https://source.mynonpublic.com/dreambox/secondstage-${PRECOMPILED_ARCH}-${PRECOMPILED_VERSION}.bin"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

require dreambox-secondstage-base.inc
