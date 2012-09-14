inherit qt4e
require qtwebkit.inc

PR = "${INC_PR}.0"

SRC_URI = " \
        http://pkgs.fedoraproject.org/repo/pkgs/qtwebkit/qtwebkit-2.2.0-rc1.tar.xz/5c1581052ad5bb7aed07a1798a340061/qtwebkit-2.2.0-rc1.tar.xz \
        file://0001-Qt-Fix-build-with-QT_LIBINFIX.patch \
"

SRC_URI[md5sum] = "5c1581052ad5bb7aed07a1798a340061"
SRC_URI[sha256sum] = "fd33bdf565dde335bf0cd686c2513c60305f95a1e4b9a82f6b305cb672985a0b"

S = "${WORKDIR}/webkit-qtwebkit"
