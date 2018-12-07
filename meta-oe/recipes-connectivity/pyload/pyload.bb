SUMMARY = "pyLoad is a fast, lightweight and full featured download manager for many One-Click-Hoster"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d4333f07cbfa8fe036e90820f556b2ad"
HOMEPAGE = "http://pyload.org/"
RDEPENDS_${PN} = "\
  python-compression \
  python-db \
  python-email \
  python-html \
  python-imaging \
  python-numbers \
  python-pprint \
  python-pycrypto \
  python-pycurl \
  python-sqlite3 \
  python-subprocess \
  python-terminal \
  python-unixadmin \
  python-xmlrpc \
"
RRECOMMENDS_${PN} = "unrar"

PV = "0.4.9"

inherit update-rc.d

SRC_URI = "git://git@github.com/pyload/pyload.git;protocol=https;branch=master \
  file://pyload.init \
  file://pyload.tar.gz.defaults"
SRCREV = "c8f2177a5c38d5289f35bd2c26c2502e9d0ff30d"

S = "${WORKDIR}/pyload"

FILES_${PN} = "/usr/pyload/* /etc/*"

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults 60 "

do_compile() {
    python -m compileall ${S}
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

