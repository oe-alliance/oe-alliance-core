SRCDATE = "20171102"

require octagon-initrd.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-sf5008  ]; then
    install -m 0644 vmlinuz-initrd-sf5008  ${DEPLOYDIR}/${MACHINE}_update.bin
    fi
}

SRC_URI[md5sum] = "4786f96ffd663d5980e96e37d1560700"
SRC_URI[sha256sum] = "6abcb82847e116dbc4e2906dc6431f4ac1ee17647daf0cebda3fc6fd328509bb"
