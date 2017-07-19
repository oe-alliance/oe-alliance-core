require et-initrd.inc

SRCDATE = "20170628"
UPDATE_NAME = "update-${MACHINE}-${SRCDATE}.bin"
SRC_URI = "https://peteru.net/xtrend/${UPDATE_NAME}"

inherit deploy
do_deploy() {
    install -m 0644 ${UPDATE_NAME} ${DEPLOYDIR}/${MACHINE}_update.bin
}

SRC_URI[md5sum] = "b3fde294f1e24442ef1b7555f0c6f3af"
SRC_URI[sha256sum] = "f24cdbd400ad93be763fafd9d0868632d1082afe17d9253b8d810d76c3380293"
