SRCDATE = "20170911"

require octagon-initrd.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-sf4008  ]; then
    install -m 0644 vmlinuz-initrd-sf4008  ${DEPLOYDIR}/${MACHINE}_update.bin
    fi
}

SRC_URI[md5sum] = "c720a2307a1178e6fdbbb937a39c3117"
SRC_URI[sha256sum] = "2ce2d6786bddee621951cd0de3ff428d5e21a8bc9001bd0ac600bd56152c2fff"
