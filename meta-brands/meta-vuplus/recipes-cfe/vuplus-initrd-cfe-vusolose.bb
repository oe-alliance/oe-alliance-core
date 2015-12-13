SRCDATE = "20150821"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7241b0 ]; then
    install -m 0644 vmlinuz-initrd-7241b0 ${DEPLOYDIR}/initrd_cfe_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "908f5bd70cb3d6be3b002393fc7e797a"
SRC_URI[sha256sum] = "cf0d93b11468636c3a1d3f6a3beea50f6027a59aabf0f73109367755bcd00add"