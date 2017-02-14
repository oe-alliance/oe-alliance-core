SRCDATE = "20170209"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7445d0 ]; then
    install -m 0644 vmlinuz-initrd-7445d0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "eb886ac801f585b04cffc218c44b60bf"
SRC_URI[sha256sum] = "ec268609e4d062dd0e75d249445d41589e2d7cf68521642102cc8fd91c3a8161"
