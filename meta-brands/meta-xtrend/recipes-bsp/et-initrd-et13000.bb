SRCDATE = "20170906"

SRC_URI = "http://source.mynonpublic.com/xtrend/vmlinuz-initrd-${MACHINE}-${SRCDATE}.zip"

require et-initrd.inc

inherit deploy
do_deploy() {
    if [ -e update.bin  ]; then
    install -m 0644 update.bin  ${DEPLOYDIR}/${MACHINE}_update.bin
    fi
}

SRC_URI[md5sum] = "0c32eae195397157ad53a20bc58780b3"
SRC_URI[sha256sum] = "49a8c0127800fbedbe1d2d4f29cbcda527c4a385cd157f6c2f530215b7174829"
