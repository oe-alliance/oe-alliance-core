SUMMARY = "OE-Alliance Python Core - Python3 base modules for enigma2"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} = "\
    python3-compat2 \
    python3-twisted-protocols \
    python3-numbers \
    python3-puremagic \
    python3-pillow \
    python3-service-identity \
    python3-requests \
    python3-future \
    python3-pexpect \
    python3-six \
    python3-trio \
    python3-compression \
    python3-process \
    "
