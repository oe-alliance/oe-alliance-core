SRCDATE = "20171013"

require octagon-initrd.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-sf5008  ]; then
    install -m 0644 vmlinuz-initrd-sf5008  ${DEPLOYDIR}/${MACHINE}_update.bin
    fi
}

SRC_URI[md5sum] = "0e596a783169efcfbf358e933c7f14d0"
SRC_URI[sha256sum] = "775f6413544a67c5918ddbda3cf77bf9289b5bbaed6dcc038086ded79b24c826"
