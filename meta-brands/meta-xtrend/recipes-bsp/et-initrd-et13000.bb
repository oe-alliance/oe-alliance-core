SRCDATE = "20170628"

SRC_URI = "http://source.mynonpublic.com/xtrend/vmlinuz-initrd-${MACHINE}-${SRCDATE}.zip"

require et-initrd.inc

inherit deploy
do_deploy() {
    if [ -e update.bin  ]; then
    install -m 0644 update.bin  ${DEPLOYDIR}/${MACHINE}_update.bin
    fi
}

SRC_URI[md5sum] = "a9fa986e6737aad776fc3914023c9ad6"
SRC_URI[sha256sum] = "1406253166e96ac50a8e8a9592e3fa1223573d0b8eae7707819d86a609eae9df"
