SRCDATE = "20161118"

require octagon-initrd.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-sf4008  ]; then
    install -m 0644 vmlinuz-initrd-sf4008  ${DEPLOYDIR}/${MACHINE}_update.bin
    fi
}

SRC_URI[md5sum] = "0d97b21a7555deee02c3c9dee2c2e0eb"
SRC_URI[sha256sum] = "4a4b48fbf83b7e3d96676bfe86cfb31bdb78136f138b5d843636fa6ffffa3ee9"
