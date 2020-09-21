SRCDATE = "20200326"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7445d0 ]; then
    install -m 0644 vmlinuz-initrd-7445d0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "9daa46bc7e318e5bcbd4524e7f7ba013"
SRC_URI[sha256sum] = "0d78b95dfa3ae156aa1142393eb6bd624ee7bdace98de6a3c5a9ef9b3533f04f"
