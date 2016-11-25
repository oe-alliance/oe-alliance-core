SRCDATE = "20161011"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7439b0 ]; then
    install -m 0644 vmlinuz-initrd-7439b0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "4f25e392d807d95c8ddf65b55555a4ab"
SRC_URI[sha256sum] = "9286713a2d2b6cf65ee5926f2d26ff44089b344f208d801bf9687b38125fb71b"
