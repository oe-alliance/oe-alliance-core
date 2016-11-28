SRCDATE = "20161011"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7445d0 ]; then
    install -m 0644 vmlinuz-initrd-7445d0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "997063e4f91d4b301fe476e65aca56a6"
SRC_URI[sha256sum] = "ab6d4b4364333817f0a47d1e07d2214e732f96c7bafa94ce56619ac27c68f0ef"
