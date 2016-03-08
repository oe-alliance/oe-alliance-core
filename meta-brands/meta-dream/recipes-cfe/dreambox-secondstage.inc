PRECOMPILED_ARCH ?= "${MACHINE}"
PRECOMPILED_VERSION ?= "${PV}"

SRC_URI = "http://sources.dreamboxupdate.com/download/7020/secondstage-${PRECOMPILED_ARCH}-${PRECOMPILED_VERSION}.bin"

S = "${WORKDIR}"

require dreambox-secondstage-base.inc
