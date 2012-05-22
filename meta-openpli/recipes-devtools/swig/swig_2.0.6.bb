require recipes-devtools/swig/swig.inc

LIC_FILES_CHKSUM = "file://LICENSE;md5=e7807a6282784a7dde4c846626b08fc6 \
                    file://LICENSE-GPL;md5=d32239bcb673463ab874e80d47fae504 \
                    file://LICENSE-UNIVERSITIES;md5=8ce9dcc8f7c994de4a408b205c72ba08"

DEPENDS += "libpcre-native"
PR = "${INC_PR}.0"

SRC_URI[md5sum] = "86bc02218774ca75bdf7766db74a62c6"
SRC_URI[sha256sum] = "dd376331dd76899736852881f0fc5ba874b0d79e88a5bd9b366bcb20e7fbb17d"
