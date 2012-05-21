require recipes-devtools/swig/swig.inc

LIC_FILES_CHKSUM = "file://LICENSE;md5=e7807a6282784a7dde4c846626b08fc6 \
                    file://LICENSE-GPL;md5=d32239bcb673463ab874e80d47fae504 \
                    file://LICENSE-UNIVERSITIES;md5=8ce9dcc8f7c994de4a408b205c72ba08"

DEPENDS += "libpcre-native"
PR = "${INC_PR}.0"

SRC_URI[md5sum] = "4319c503ee3a13d2a53be9d828c3adc0"
SRC_URI[sha256sum] = "763a117730d26f8e5ed67f5718c6c0761fbb8461680fc20269db8c0839e1ec8a"
