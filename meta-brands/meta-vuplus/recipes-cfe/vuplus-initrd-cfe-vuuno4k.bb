SRCDATE = "20170209"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7439b0 ]; then
    install -m 0644 vmlinuz-initrd-7439b0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "2b972c86995ad48212a25386966658a4"
SRC_URI[sha256sum] = "c383a7f67d05655c54822b1f7a89e54601831efdb530ecb6b903320e9539e9fa"
