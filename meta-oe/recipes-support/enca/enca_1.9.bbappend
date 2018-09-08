SRC_URI = "https://src.fedoraproject.org/lookaside/extras/enca/enca-1.9.tar.bz2 \
        file://configure-hack.patch \
        file://dont-run-tests.patch \
        file://configure-remove-dumbness.patch \
        file://makefile-remove-tools.patch \
        file://libenca-003-iconv.patch "

SRC_URI[md5sum] = "b3581e28d68d452286fb0bfe58bed3b3"
SRC_URI[sha256sum] = "02acfef2b24a9c842612da49338138311f909f1cd33933520c07b8b26c410f4d"
