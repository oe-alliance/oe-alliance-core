SUMMARY = "Firmware for the GTX MT7610U kernel driver"
HOMEPAGE = "https://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git/"
LICENSE = "LicenseRef-Firmware-mediatek"
LIC_FILES_CHKSUM = "file://LICENCE.mediatek-${PV};md5=7c1976b63217d76ce47d0a11d8a79cf2"
NO_GENERIC_LICENSE[Firmware-mediatek] = "LICENCE.mediatek-${PV}"

inherit allarch

COMPATIBLE_MACHINE = "^gtxcombo$"

FIRMWARE_BASE_URI = "https://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git/plain"
SRC_URI = " \
    ${FIRMWARE_BASE_URI}/mediatek/mt7610u.bin?h=${PV};name=mt7610u;downloadfilename=mt7610u-${PV}.bin \
    ${FIRMWARE_BASE_URI}/mediatek/mt7610e.bin?h=${PV};name=mt7610e;downloadfilename=mt7610e-${PV}.bin \
    ${FIRMWARE_BASE_URI}/LICENCE.mediatek?h=${PV};name=license;downloadfilename=LICENCE.mediatek-${PV} \
"
SRC_URI[mt7610u.sha256sum] = "5a4268e9021bb587426ba624b425f1e660bfc82cd63b36ad3ce6fb9ce6751760"
SRC_URI[mt7610e.sha256sum] = "538d08c08f8fb5aa05cacf34e49aeb307e8b2dc094407fc655fbf3580c893953"
SRC_URI[license.sha256sum] = "a90d3f66704d85889945fec5525ea77622549da83aced1aac99828383f8f1805"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/mediatek
    install -m 0644 ${S}/mt7610u-${PV}.bin ${D}${nonarch_base_libdir}/firmware/mediatek/mt7610u.bin
    install -m 0644 ${S}/mt7610e-${PV}.bin ${D}${nonarch_base_libdir}/firmware/mediatek/mt7610e.bin
    install -m 0644 ${S}/LICENCE.mediatek-${PV} ${D}${nonarch_base_libdir}/firmware/LICENCE.mediatek
}

FILES:${PN} = "${nonarch_base_libdir}/firmware"
