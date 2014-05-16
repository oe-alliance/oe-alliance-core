SUMMARY = "opera-hbbtv-browser"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

DEPENDS = "tslib mpfr gmp"
RDEPENDS_${PN} = "tslib-conf libts-1.0-0 libsysfs2 libgmp10 libmpfr4 enigma2-hbbtv-util vuplus-opera-dumpait"

PACKAGES =+ "${PN}-src enigma2-hbbtv-util enigma2-hbbtv-util-src"
PROVIDES =+ "enigma2-hbbtv-util"

SRC_DATE = "20140430_0"
SRC_URI = ""

PR = "r32_${SRC_DATE}"

S = "${WORKDIR}/opera-hbbtv"

INHIBIT_PACKAGE_STRIP = "1"

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

# Just a quick hack to "compile" the python parts.
do_compile_append() {
    python -O -m compileall ${S}
}

do_install() {
    rm -f ${S}/opera/lib/libopera.so

    install -d ${D}/usr/local/hbb-browser
    mv ${S}/opera/* ${D}/usr/local/hbb-browser/
    # workaround for broken startup script and segfault in libfaketime.so
    sed -i -e '1,2d' -e 's/libfaketime.so //g' ${D}/usr/local/hbb-browser/launcher

    install -d ${D}/etc
    mv ${S}/dfb/etc/* ${D}/etc/

    install -d ${D}/usr/bin
    mv ${S}/dfb/usr/bin/* ${D}/usr/bin/

    install -d ${D}/usr/lib
    mv ${S}/dfb/usr/lib/* ${D}/usr/lib/

    install -d ${D}/usr/share
    mv ${S}/dfb/usr/share/* ${D}/usr/share/

    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/HbbTV
    mv ${S}/plugin/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/HbbTV
}

do_package_qa() {
}

FILES_${PN} = "/usr/lib /usr/local /usr/share /usr/bin /etc "
FILES_enigma2-hbbtv-util = "/usr/lib/enigma2/python/Plugins/Extensions/HbbTV/*.pyo /usr/lib/enigma2/python/Plugins/Extensions/HbbTV/*.so"
FILES_enigma2-hbbtv-util-src = "/usr/lib/enigma2/python/Plugins/Extensions/HbbTV/*.py"
