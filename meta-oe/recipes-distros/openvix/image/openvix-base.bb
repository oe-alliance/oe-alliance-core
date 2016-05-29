SUMMARY = "OpenViX Base"
MAINTAINER = "OpenViX"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r1"

inherit packagegroup

DEPENDS = "openvix-version-info"

RDEPENDS_${PN} = "\
    oe-alliance-base \
    openvix-version-info \
    openvix-enigma2 \
    openvix-bootlogo \
    openvix-spinner \
    ${@bb.utils.contains("MACHINE", "vusolo4k", "glibc-compat", "", d)} \
    "

