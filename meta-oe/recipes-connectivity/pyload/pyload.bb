SUMMARY = "pyLoad is a fast, lightweight and full featured download manager for many One-Click-Hoster"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d4333f07cbfa8fe036e90820f556b2ad"
HOMEPAGE = "http://pyload.org/"
RDEPENDS_${PN} = "\
  ${PYTHON_PN}-compression \
  ${PYTHON_PN}-db \
  ${PYTHON_PN}-email \
  ${PYTHON_PN}-html \
  ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-imaging", "${PYTHON_PN}-pillow", d)} \
  ${PYTHON_PN}-numbers \
  ${PYTHON_PN}-pprint \
  ${PYTHON_PN}-pycryptodome \
  ${PYTHON_PN}-pycurl \
  ${PYTHON_PN}-sqlite3 \
  ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-subprocess", "", d)} \
  ${PYTHON_PN}-terminal \
  ${PYTHON_PN}-unixadmin \
  ${PYTHON_PN}-xmlrpc \
"
RRECOMMENDS_${PN} = "unrar"

PV = "0.4.9"

inherit update-rc.d

SRC_URI = "http://source.mynonpublic.com/pyload-src-v${PV}.zip \
  file://pyload.init \
  file://pyload.tar.gz.defaults"
SRC_URI[md5sum] = "28876150af22999b6f539c8579d3b415"
SRC_URI[sha256sum] = "f937631d376216bc830d6ffcd5b4ecb1806afd4012a184849da1a333a7ba0016"

S = "${WORKDIR}/pyload"

FILES_${PN} = "/usr/pyload/* /etc/*"

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults 60 "

do_compile() {
    python2 -m compileall ${S}
}

do_install() {
    install -d ${D}/etc/init.d
    install -d ${D}/usr/pyload

    cp -r ${S}/icons ${D}/usr/pyload
    cp -r ${S}/locale ${D}/usr/pyload
    cp -r ${S}/module ${D}/usr/pyload
    cp -r ${S}/scripts ${D}/usr/pyload
    install -m 755 ${S}/pyLoadCli.py ${D}/usr/pyload
    install -m 755 ${S}/pyLoadCore.py ${D}/usr/pyload
    install -m 755 ${S}/systemCheck.py ${D}/usr/pyload
    cp ${WORKDIR}/pyload.tar.gz.defaults ${D}/usr/pyload/pyload-defaults.tar.gz
    
    install -m 0755 ${WORKDIR}/pyload.init ${D}/etc/init.d/pyload
}

