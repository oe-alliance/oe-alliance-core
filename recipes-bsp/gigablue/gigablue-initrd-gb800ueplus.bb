SRCDATE = "20140116"

require gigablue-initrd.inc

inherit deploy
do_deploy() {
    if [ -e initrd.bin ]; then
    install -m 0644 initrd.bin ${DEPLOYDIR}/initrd.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "314f83cc4b93eae7f07ce4a783278bf4"
SRC_URI[sha256sum] = "eb569952c62b4124a70298cb6e7b703b002d3c35b62b873558d97827d5d2740d"
