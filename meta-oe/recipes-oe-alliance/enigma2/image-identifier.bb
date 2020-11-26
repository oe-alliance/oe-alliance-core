SUMMARY = "Image identifier to help multiboot"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "oe-alliance team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATE}-${BUILD_VERSION}-${MACHINEBUILD}"
PACKAGE_ARCH = "${MACHINEBUILD}"
WORKDIR = "${TMPDIR}/work/${MULTIMACH_TARGET_SYS}/${PN}/${EXTENDPE}${PV}"
do_configure[nostamp] = "1"

S = "${WORKDIR}"

PACKAGES = "${PN}"

do_install() {
    install -d ${D}/usr/lib/enigma2/python
    printf "def getBoxType(): return '${MACHINEBUILD}'
" > ${D}/usr/lib/enigma2/python/ImageIdentifier.py
    printf "def getImageDistro(): return '${DISTRO_NAME}'
" >> ${D}/usr/lib/enigma2/python/ImageIdentifier.py
    printf "def getImageVersion(): return '${IMAGE_VERSION}'
" >> ${D}/usr/lib/enigma2/python/ImageIdentifier.py
    printf "def getImageBuild(): return '${BUILD_VERSION}'
" >> ${D}/usr/lib/enigma2/python/ImageIdentifier.py
    printf "def getImageDevBuild(): return '${DEVELOPER_BUILD_VERSION}'
" >> ${D}/usr/lib/enigma2/python/ImageIdentifier.py
    printf "def getImageType(): return '${DISTRO_TYPE}'
" >> ${D}/usr/lib/enigma2/python/ImageIdentifier.py
    printf "def getMachineBrand(): return '${MACHINE_BRAND}'
" >> ${D}/usr/lib/enigma2/python/ImageIdentifier.py
    printf "def getImageBuildDate(): return '${DATE}'
" >> ${D}/usr/lib/enigma2/python/ImageIdentifier.py
}
FILES_${PN} += "/usr/lib/enigma2/python/ImageIdentifier.py"
