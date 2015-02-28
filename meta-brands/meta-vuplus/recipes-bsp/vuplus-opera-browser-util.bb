SUMMARY = "opera-hbbtv-browser"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

DEPENDS = "tslib mpfr gmp"
RDEPENDS_${PN} = "tslib-conf libts-1.0-0 libsysfs2 libgmp10 libmpfr4 vuplus-opera-dumpait"

SRC_DATE = "20150106_0"
SRC_URI = ""

PR = "r35_${SRC_DATE}"

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
    if [[ ! -e ${DL_DIR}/${SRC_FILE} && -e /etc/vuplus_browser.pwd ]]; then
sshpass -f /etc/vuplus_browser.pwd sftp -o StrictHostKeyChecking=no guestuser@code.vuplus.com << +
get ${SRC_FILE}
bye
+
    fi
    [ -f ${DL_DIR}/${SRC_FILE} ] && cp -av ${DL_DIR}/${SRC_FILE} ${WORKDIR}/ || true
}

do_unpack() {
    [ -f ${DL_DIR}/${SRC_FILE} ] && tar xvfz ${SRC_FILE} || true
}

do_compile() {
}

do_install() {
    if [[ -d ${S}/opera ]]; then
    install -d ${D}/usr/local/hbb-browser
    mv ${S}/opera/* ${D}/usr/local/hbb-browser/
    install -d ${D}/usr/lib
    mv ${S}/dfb/usr/lib/* ${D}/usr/lib/
    fi
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
