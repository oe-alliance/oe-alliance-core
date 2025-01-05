inherit upx-compress

PACKAGES =+ "${PN}-full"
FILES:${PN}-full = "${bindir}/7z ${bindir}/7z.real ${bindir}/7z.so ${bindir}/7zr ${libdir}/lib7z.so"

RRECOMMENDS:${PN}-full += "${BPN}"

INSANE_SKIP:${PN}-full += "dev-so"
