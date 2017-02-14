SRCDATE = "20170209"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7366c0 ]; then
    install -m 0644 vmlinuz-initrd-7366c0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "5baa24532311015aeb2b077ac23b9198"
SRC_URI[sha256sum] = "9180d7e73cf1f419a7919622e05092aacbc4c8fc7e876cc041d2dd4e7c2e2205"
