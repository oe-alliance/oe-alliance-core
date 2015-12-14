SRCDATE = "20150821"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7241b0 ]; then
    install -m 0644 vmlinuz-initrd-7241b0 ${DEPLOYDIR}/initrd_cfe_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "eb5efad0435e7c455a2e8bf1a56eb551"
SRC_URI[sha256sum] = "e374477d429c901611e81c4b38d32228aa69f42edb002d0d555023d9ba6a6816"