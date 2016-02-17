SUMMARY = "opera-hbbtv-browser"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc

DEPENDS = "tslib mpfr gmp libcrypto0.9.8 ${@base_contains("GST_VERSION", "1.0", "gstreamer1.0", "gstreamer", d)}"
RDEPENDS_${PN} = "libsysfs2 libgmp10 libmpfr4 vuplus-opera-dumpait libcrypto0.9.8"

SRC_DATE = "20151222_0"
SRC_URI = ""

PR = "r42_${SRC_DATE}"

GSTVER = "${@base_contains("GST_VERSION", "1.0", "1.4.5", "0.10.36.1", d)}"

S = "${WORKDIR}/opera-hbbtv"

INHIBIT_PACKAGE_STRIP = "1"
PRIVATE_LIBS_${PN} = "libopera_hbbtv.so \
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

SRC_FILE = "opera-hbbtv_${SRC_DATE}.tar.gz"
do_fetch() {
    if [ ! -e ${DL_DIR}/${SRC_FILE} -a -e /etc/vuplus_browser.pwd ]; then
sshpass -f /etc/vuplus_browser.pwd sftp -o StrictHostKeyChecking=no guestuser@code.vuplus.com << +
get ${SRC_FILE}
bye
+
    fi
    cp -av ${DL_DIR}/${SRC_FILE} ${WORKDIR}/
}

do_unpack() {
    tar xvfz ${SRC_FILE}
}

do_compile() {
}

do_install() {
    install -d ${D}/usr/local/hbb-browser
    mv ${S}/opera/* ${D}/usr/local/hbb-browser/
    install -d ${D}/usr/lib
    mv ${S}/dfb/usr/lib/* ${D}/usr/lib/
    mv ${D}/usr/local/hbb-browser/root/jsplugins/ooif-gst-${GSTVER}.so ${D}/usr/local/hbb-browser/root/jsplugins/ooif.so
    rm -f ${D}/usr/local/hbb-browser/root/jsplugins/ooif-gst*.so
    mv ${D}/usr/local/hbb-browser/root/video/videobackend-gst-${GSTVER}.so ${D}/usr/local/hbb-browser/root/video/videobackend.so
    rm -f ${D}/usr/local/hbb-browser/root/video/videobackend-gst*.so
}

package_do_shlibs_append() {
    deps = "${PKGDEST}/${PN}.shlibdeps"
    tmp = "/tmp/.${PN}.shlibdeps"
    os.system("sed -e '/vbrowser/d' %s > %s" % (deps, tmp))
    os.system("cp %s %s" % (tmp, deps))
}

do_package_qa() {
}

sysroot_stage_all() {
}

FILES_${PN} = "/usr/*"
