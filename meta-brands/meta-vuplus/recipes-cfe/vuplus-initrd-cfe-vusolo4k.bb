SRCDATE = "20170112"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7366c0 ]; then
    install -m 0755 vmlinuz-initrd-7366c0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "b3e6a9df20211076adbe2f33b8bbad87"
SRC_URI[sha256sum] = "c471b241f7d14ae401bfdd940307739d38b94213286a450078321def04f717a0"
