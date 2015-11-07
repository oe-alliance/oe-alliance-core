SRCDATE = "20150901"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7366b0 ]; then
    install -m 0644 vmlinuz-initrd-7366b0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "825b9e90b49742358ec162db13e75d9d"
SRC_URI[sha256sum] = "d5fa2dd43cca9070e0be857ddbc57ed82b918a23c5daa9b4243aeee4c33e3911"
