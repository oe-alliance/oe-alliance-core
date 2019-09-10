inherit upx-compress

EXTRA_OEMAKE = "all3"

do_install() {
    install -d ${D}${bindir}
    install -d ${D}${bindir}/Codecs
    install -m 0755 ${S}/bin/7* ${D}${bindir}
    install -m 0755 ${S}/bin/Codecs/* ${D}${bindir}/Codecs

    # Create a shell script wrapper to execute next to 7z.so
    mv ${D}${bindir}/7z ${D}${bindir}/7z.bin
    cat > ${D}${bindir}/7z << 'EOF'
#!/bin/sh
exec "$(dirname "$0")"/7z.bin "$@"
EOF
    chmod 0755 ${D}${bindir}/7z
}

PACKAGES =+ "${PN}-full"
FILES_${PN}-full = "${bindir}/7z ${bindir}/7z.bin ${bindir}/7z.so ${bindir}/7zr ${bindir}/*.sfx ${bindir}/Codecs"

RRECOMMENDS_${PN}-full += "${BPN}"
