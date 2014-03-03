SRCDATE = "20140116"

require gigablue-initrd.inc

inherit deploy
do_deploy() {
    if [ -e initrd.bin ]; then
    install -m 0644 initrd.bin ${DEPLOYDIR}/initrd.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "b7447cc9f92996110456bf0799daba7c"
SRC_URI[sha256sum] = "ed86583e15bedb2da762e07a8f2ac2a1767758d73924dc88fbd66bac242a6858"
