SRCDATE = "20180108"

require octagon-initrd.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-sf4008  ]; then
    install -m 0644 vmlinuz-initrd-sf4008  ${DEPLOYDIR}/${MACHINE}_update.bin
    fi
}

SRC_URI[md5sum] = "61835d3b079f31440e4af68614391e9f"
SRC_URI[sha256sum] = "7bbd5467d3780d4f57c505d076fce15ced384955abc5d260d362c7fe34fda3ac"
