SRCDATE = "20181030"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7278b1 ]; then
    install -m 0644 vmlinuz-initrd-7278b1 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "2280778c1a445ea7727268597ac559be"
SRC_URI[sha256sum] = "42d3bac078edfc0c80d82caad708fdb4eca5cef5d130e7fc2e6fafdb11e66b64"
