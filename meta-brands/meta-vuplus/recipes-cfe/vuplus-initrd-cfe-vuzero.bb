SRCDATE = "20141117"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7362a0 ]; then
    install -m 0644 vmlinuz-initrd-7362a0 ${DEPLOYDIR}/initrd_cfe_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "670219b09ba2cace6ebe0492e514f340"
SRC_URI[sha256sum] = "fb23c93b727dd89e767e1d52c879045d8f40aecad049ec1d5b917843dfb0930e"