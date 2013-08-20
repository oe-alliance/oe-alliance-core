SRCDATE = "20130212"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7346b0 ]; then
    install -m 0644 vmlinuz-initrd-7346b0 ${DEPLOYDIR}/initrd_cfe_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "3b45489e7902cbf98e9abdddea14567a"
SRC_URI[sha256sum] = "e7a7e747dcd7240c5d36c2235d11b2d0e703ed55be1120d6109220478d23fb09"
