SRCDATE = "20170112"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7445d0 ]; then
    install -m 0755 vmlinuz-initrd-7445d0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "e9b7f1f9350d92ed06c45bc9933b1ec6"
SRC_URI[sha256sum] = "8d844e0c44d458ede6837d499504fff328e9d869af88515c684036cf6d52f176"
