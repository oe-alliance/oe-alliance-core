SUMMARY = "Image identifier to help multiboot"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "oe-alliance team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}-${BUILD_VERSION}"
PR = "r${DATE}"

PACKAGE_ARCH = "${MACHINEBUILD}"

PACKAGES = "${PN}"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_compile() {
    mkdir -p ${S}/usr/lib/enigma2/python
    printf "def getBoxType(): return '${MACHINEBUILD}'
" > ${S}/usr/lib/enigma2/python/ImageIdentifier.py
    printf "def getImageDistro(): return '${DISTRO_NAME}'
" >> ${S}/usr/lib/enigma2/python/ImageIdentifier.py
    printf "def getImageVersion(): return '${IMAGE_VERSION}'
" >> ${S}/usr/lib/enigma2/python/ImageIdentifier.py
    printf "def getImageBuild(): return '${BUILD_VERSION}'
" >> ${S}/usr/lib/enigma2/python/ImageIdentifier.py
    printf "def getImageDevBuild(): return '${DEVELOPER_BUILD_VERSION}'
" >> ${S}/usr/lib/enigma2/python/ImageIdentifier.py
    printf "def getImageType(): return '${DISTRO_TYPE}'
" >> ${S}/usr/lib/enigma2/python/ImageIdentifier.py
    printf "def getMachineBrand(): return '${MACHINE_BRAND}'
" >> ${S}/usr/lib/enigma2/python/ImageIdentifier.py
}

do_install() {
    install -d ${D}/usr/lib/enigma2/python
    install -m 0644 ${S}/usr/lib/enigma2/python/* ${D}/usr/lib/enigma2/python
}

FILES:${PN} += "/usr/lib/enigma2/python/ImageIdentifier.py"
