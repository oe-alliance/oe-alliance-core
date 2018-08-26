SRCREV = ""
PV = "1.0.4"

SRC_URI = "http://sources.openelec.tv/mirror/lockdev/lockdev-16b8996.tar.xz \
            file://0001-include-sys-sysmacros.h-for-major-minor-definitions.patch"

SRC_URI[md5sum] = "8c99733cc181fc9900b907ca116f62f1"
SRC_URI[sha256sum] = "49900093c12099047afa9f9d341da07b1a4a719e35c43db8409f65555ce09eb4"

S = "${WORKDIR}/lockdev-16b8996"
