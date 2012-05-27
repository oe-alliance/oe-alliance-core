SUMMARY = "Dreambox watchdog to automatically restart binaries"
PR = "r0"

SRC_URI[mips32el.md5sum] = "037fcab221a559ec765ce58943c4e95c"
SRC_URI[mips32el.sha256sum] = "96f3bcb25599562c57f3e451cfc884e39b924b6998032b0b562c66430d271483"
SRC_URI[mips32el-nf.md5sum] = "628c9db98032e6bbc942ab5fc94a39db"
SRC_URI[mips32el-nf.sha256sum] = "83b8b3e61c3695e527f42e446628e4b9a8d23dcfe8a1949bfe07315706fc7078"

inherit opendreambox-precompiled-binary

do_install() {
        install -d ${D}${bindir}
        install -m 0755 wdog ${D}${bindir}
}
