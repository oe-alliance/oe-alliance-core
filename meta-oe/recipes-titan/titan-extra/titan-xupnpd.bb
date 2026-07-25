DESCRIPTION = "xupnpd - eXtensible UPnP agent"
HOMEPAGE = "http://xupnpd.org"

MAINTAINER = "TitanNit Developer"
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

DEPENDS = " \
	libupnp \
    readline \
    openssl \
	"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"

SRC_URI="git://github.com/clark15b/xupnpd.git;protocol=https;branch=master \
        file://liblua.makefile.patch \
        file://xupnpd.patch"

S = "${WORKDIR}/git/src"

SRC     = "main.cpp soap.cpp mem.cpp mcast.cpp luaxlib.cpp luaxcore.cpp luajson.cpp luajson_parser.cpp"
LUAMYCFLAGS = "-DLUA_USE_LINUX"
LUA = "lua-5.3.5"
#LUA = "lua-5.1.4"
CFLAGS:append = " -DLUA_USE_LINUX -fno-exceptions -fno-rtti -O2 -I${LUA} -L${LUA}"
LDFLAGS:prepend = " -llua -lm -ldl -lssl -lcrypto "

do_compile() {
#	cd ${WORKDIR}/git/src
#    make clean
#	cd ${WORKDIR}/git/src/${LUA}
#    make linux clean

	cd ${WORKDIR}/git/src

#	make linux -C ${LUA}
	${LIBEXEC} make linux -C ${LUA}
#	${LIBEXEC} make -C ${LUA} a MYCFLAGS='${LUAMYCFLAGS}'
	${CC} -O2 -c -o md5.o md5c.c
	${CC} ${CFLAGS} -DWITH_URANDOM -o xupnpd ${SRC} md5.o ${LDFLAGS}
	${STRIP} xupnpd
}

FILES:${PN} = " \
    /etc/init.d \
	/usr/bin \
    /usr/share/xupnpd \
"

do_install() {
  install -d ${D}/usr/bin ${D}/usr/share/xupnpd ${D}/usr/share/xupnpd/config ${D}/usr/share/xupnpd/playlists ${D}/etc/init.d
  cp ${S}/contrib/OpenEmbedded/files/xupnpd.init ${D}/etc/init.d/xupnpd

  cp ${S}/xupnpd ${D}/usr/bin/
  cp -r ${S}/plugins ${D}/usr/share/xupnpd/
  cp -r ${S}/profiles ${D}/usr/share/xupnpd/
  cp -r ${S}/ui ${D}/usr/share/xupnpd/
  cp -r ${S}/www ${D}/usr/share/xupnpd/
  cp ${S}/*.lua ${D}/usr/share/xupnpd/
#  cp ${S}/contrib/OpenEmbedded/files/xupnpd.lua ${D}/usr/share/xupnpd/
}

