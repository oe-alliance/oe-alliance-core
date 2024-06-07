SUMMARY = "opera-hbbtv-browser"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc

DEPENDS = "sshpass-native tslib mpfr gmp libcrypto0.9.8 gstreamer1.0"
RDEPENDS:${PN} = "libsysfs2 libgmp10 libmpfr4 vuplus-opera-dumpait libcrypto0.9.8"

SRC_DATE = "20181116_0"
SRC_URI = ""

PR = "r47_${SRC_DATE}"

GSTVER = "1.4.5"

S = "${WORKDIR}/opera-hbbtv"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
PRIVATE_LIBS:${PN} = "libopera_hbbtv.so \
libdsmcc.so \
libdirect-1.4.so.6 \
libdirectfb-1.4.so.6 \
libfusion-1.4.so.6 \
libdirectfbwm_default.so \
libdirectfb_linux_input.so \
libdirectfb_devmem.so \
libdirectfb_dummy.so \
libdirectfb_fbdev.so \
libidirectfbfont_dgiff.so \
libidirectfbvideoprovider_v4l.so \
libidirectfbvideoprovider_gif.so \
libidirectfbimageprovider_dfiff.so \
libidirectfbimageprovider_gif.so \
libidirectfbimageprovider_jpeg.so \
libicoreresourcemanager_test.so \
libdirectfb_vuplus.so"

SRC_URI = "https://source.mynonpublic.com/opera-hbbtv_${SRC_DATE}.tar.gz"

SRC_URI[md5sum] = "aa99fb26e817f8fa49df9c1cd1fcb9fe"
SRC_URI[sha256sum] = "d85ed5b797cb963b219c7e594d1a57361220e90d7fa7269bb84f2d0e6ef13b3e"

do_compile() {
}

do_install() {
    if [[ -d ${S}/opera ]]; then
        install -d ${D}/usr/local/hbb-browser
        cp -rf ${S}/opera/* ${D}/usr/local/hbb-browser/
        install -d ${D}/usr/lib
        cp -rf ${S}/dfb/usr/lib/* ${D}/usr/lib/
        mv ${D}/usr/local/hbb-browser/root/jsplugins/ooif-gst-${GSTVER}.so ${D}/usr/local/hbb-browser/root/jsplugins/ooif.so
        rm -f ${D}/usr/local/hbb-browser/root/jsplugins/ooif-gst*.so
        mv ${D}/usr/local/hbb-browser/root/video/videobackend-gst-${GSTVER}.so ${D}/usr/local/hbb-browser/root/video/videobackend.so
        rm -f ${D}/usr/local/hbb-browser/root/video/videobackend-gst*.so
    fi
}

package_do_shlibs:append() {
    deps = "${PKGDEST}/${PN}.shlibdeps"
    tmp = "/tmp/.${PN}.shlibdeps"
    os.system("sed -e '/vbrowser/d' %s > %s" % (deps, tmp))
    os.system("cp %s %s" % (tmp, deps))
}

do_package_qa() {
}

sysroot_stage_all() {
}

FILES:${PN} = "/usr/*"
