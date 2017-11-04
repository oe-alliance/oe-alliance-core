SRCDATE = "20170627"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7439b0 ]; then
    install -m 0644 vmlinuz-initrd-7439b0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "8f96e5527503a4fe776c69887e93d613"
SRC_URI[sha256sum] = "055e0a3b1fed3a524f2ae41b93d77a024effcddf85dcbee9857749366d0823a4"
