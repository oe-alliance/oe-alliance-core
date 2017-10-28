LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

RDEPENDS_${PN} += "connman"
RRECOMMENDS_${PN} += "connman-client"

RPROVIDES_${PN} += "packagegroup-net"
RCONFLICTS_${PN} += "packagegroup-net"
