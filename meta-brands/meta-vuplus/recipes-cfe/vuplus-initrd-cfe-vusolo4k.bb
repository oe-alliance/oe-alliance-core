SRCDATE = "20160226"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7366b0 ]; then
    install -m 0644 vmlinuz-initrd-7366b0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "8ca9150fec96c9409b142ec9a4b7891e"
SRC_URI[sha256sum] = "9d8dfbb14e98f42c76f12e80814e06dec33f4a1f93f31b3f279f95f86aa9b8fc"
