SUMMARY = "SM1 hardware AVC and HEVC encoder libraries"
HOMEPAGE = "https://github.com/khadas/libencoder"
require conf/license/license-close.inc
COMPATIBLE_MACHINE = "^gtxcombo$"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = "bfee62dad4f7ebb6d1705df8522da871dcad861e"
SRC_URI = "git://github.com/khadas/libencoder.git;protocol=https;branch=yocto-kirkstone-202406-smarthome;destsuffix=libencoder file://0001-sm1-userspace-abi.patch"
S = "${UNPACKDIR}/libencoder"

do_compile() {
    cd ${S}/h264/bjunion_enc
    oe_runmake CC="${CC}" CXX="${CXX}" CFLAGS="${CFLAGS} -fPIC" LDFLAGS="${LDFLAGS} -shared -lm -lrt" libvpcodec.so
    cd ${S}/h265/EncoderAPI-HEVC/hevc_enc
    ${CXX} ${CXXFLAGS} -fPIC -shared -Iinclude -I. *.cpp ${LDFLAGS} -Wl,-soname,libgtxhevc.so.1 -o libgtxhevc.so.1
}

do_install() {
    install -d ${D}${libdir}/gtx-venc
    install -m 0755 ${S}/h264/bjunion_enc/libvpcodec.so ${D}${libdir}/gtx-venc/libvpcodec.so
    install -m 0755 ${S}/h265/EncoderAPI-HEVC/hevc_enc/libgtxhevc.so.1 ${D}${libdir}/gtx-venc/
}
FILES:${PN} += "${libdir}/gtx-venc"
