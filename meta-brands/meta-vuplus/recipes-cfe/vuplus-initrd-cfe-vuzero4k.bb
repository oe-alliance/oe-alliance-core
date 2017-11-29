SRCDATE = "20170522"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7260a0 ]; then
    install -m 0644 vmlinuz-initrd-7260a0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "fb602c04f92d99c4109cdb4578dfb614"
SRC_URI[sha256sum] = "36a469463f5adef633dc0943a73c3989ec834393ca399cf1ad0d50aad0c01455"
