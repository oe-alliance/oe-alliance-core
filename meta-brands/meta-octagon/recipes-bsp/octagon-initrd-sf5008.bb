SRCDATE = "20170915"

require octagon-initrd.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-sf5008  ]; then
    install -m 0644 vmlinuz-initrd-sf5008  ${DEPLOYDIR}/${MACHINE}_update.bin
    fi
}

SRC_URI[md5sum] = "eeaa6a74b831b7686f4bf28d5f9d6653"
SRC_URI[sha256sum] = "e62b27d308e44e6d18795cae6582b49b4cfe9902652d436897d7b6160d783c0d"
