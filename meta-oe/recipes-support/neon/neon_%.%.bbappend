PACKAGECONFIG = "expat openssl libproxy webdav zlib"
PACKAGECONFIG_class-native = "expat openssl webdav zlib"

BINCONFIG = ""

binconfig_disabled_sysroot_preprocess () {
	install -d ${SYSROOT_DESTDIR}${bindir_crossscripts}
	install ${D}${bindir}/neon-config ${SYSROOT_DESTDIR}${bindir_crossscripts}
}

