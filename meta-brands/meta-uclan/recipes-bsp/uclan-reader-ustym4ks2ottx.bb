SUMMARY = "libreader for uclan Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(ustym4ks2ottx)$"

SRCDATE = "20230217"

PV = "${SRCDATE}"
PR = "r2"

SRC_URI = "https://source.mynonpublic.com/uclan/${MACHINE}-libreader-${SRCDATE}.tar.gz"

S = "${UNPACKDIR}"

INSANE_SKIP:${PN} += "already-stripped"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_install:append() {
    python3 - <<'PY'
from pathlib import Path

reader = Path("${D}/${bindir}/libreader")

old = bytes.fromhex("0a30c5e5")
new = bytes.fromhex("0a20c5e5")

if reader.exists():
    data = reader.read_bytes()
    if old in data:
        reader.write_bytes(data.replace(old, new, 1))
PY
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "13ff947017ec392986517fa2c033d63e"
SRC_URI[sha256sum] = "8e50d896ebc87a3b041002774820f704b6dcbe36deb73316306c49ed2a36ce01"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
