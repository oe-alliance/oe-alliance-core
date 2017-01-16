SRCDATE = "20170112"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7439b0 ]; then
    install -m 0755 vmlinuz-initrd-7439b0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "f54c8fec94279a4752b2104423e69c82"
SRC_URI[sha256sum] = "e231a804420d2cb8bf387f5044c9f450abfb2a6ffe6f561400732ddd761d219f"
